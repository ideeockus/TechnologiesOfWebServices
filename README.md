1. Что такое JAXB, как аннотации JAXB можно применить в контексте SOAP-сервисов?


JAXB это способ маппить XML в Java классы (сериализация), например при помощи таких аннотаций как `@XmlRootElement` . Это упрощает процесс работы с данными SOAP-сообщений

2. В чем заключается роль утилиты wsimport, и можно ли обойтись без нее?

   `wsimport` упрощает создание клиентов для SOAP сервисов, так как может сгенерировать шаблон по WSDL описанию. Можно обойтись и без нее, но придется больше кода написать руками

3. В чем заключается роль утилиты wsgen, и можно ли обойтись без нее?

`wsgen` позволяет сгенерировать WSDL описание для SOAP сервиса. В принципе можно обойтись без нее, так как jax-ws умеет сам генерировать WSDL описание.

4. Предположим, что я разработал веб-сервис, но не смог предоставить  WSDL разработчикам программного-клиента, а предоставил только XSD.  Смогут ли они коммуницировать с моим веб-сервисом в случае, если им  известна только XSD-схема?

Без WSDL коммуницировать будет затруднительно, так как разработчикам клиента потребуется вручную писать весь код клиента (не смогут использовать wsimport). XSD (XML Schema Definition) описывает структуру и формат данных для коммуникации между клиентом и сервером. Но там нету адресов сервисов, так что коммуницировать скорее не выйдет.

5. Является ли сгенерированный JAX-WS клиент thread safe или нет? Если  нет, то как можно решить данную проблему, и коммуницировать с сервисов  из нескольких потоков? Проиллюстрируйте пример кодом.

Сгенерированный клиент не thread-safe. Решить можно, например, создавая новый инстанс на каждый запрос:

```kotlin
fun callMyMethod() {
    val service = MyWebServiceService() // новый инстанс
    val result = service.myMethod()
}
```



5. Предположим, при реализации SOAP веб-сервиса Вам необходимо  передавать двоичные данные (аватары пользователей, архивы...). Какие  стратегии Вы можете предложить для работы с binary attachments?  Реализуйте одну из них.

Например так

```kotlin
@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
interface MyWebService {
    @WebMethod
    fun uploadImage(@XmlMimeType("application/octet-stream") image: DataHandler): String

    @WebMethod
    @XmlMimeType("application/octet-stream")
    fun downloadImage(uuid: String): DataHandler
}

@WebService(endpointInterface = "org.example.MyWebService")
class MyWebServiceImpl : MyWebService {

    companion object {
        private const val STORAGE_PATH = "/var/my_storage/"
    }

    override fun uploadImage(image: DataHandler): String {
        val uuid = UUID.randomUUID().toString()
        val file = File(STORAGE_PATH + uuid)
        FileOutputStream(file).use { outputStream ->
            image.writeTo(outputStream)
        }
        return uuid
    }

    override fun downloadImage(uuid: String): DataHandler {
        val dataSource: DataSource = FileDataSource(File(STORAGE_PATH + uuid))
        return DataHandler(dataSource)
    }
}
```



1. Предположим, что одним из нефункциональных требований к вашему  сервису является поддержка throttling (умышленное ограничение количества одновременно выполняемых запросов; если поступает новый запрос, а в это время уже выполняется максимально разрешенное количество, то необходимо прервать выполнение запроса, например, путем выброса  ThrottlingException (класс исключения нужно создать самостоятельно)).  Реализуйте данный механизм в одном из ваших сервисов.

Можно создать класс `RequestThrottler` и добавить его в routing KTOR'а:

```kotlin
import java.util.concurrent.atomic.AtomicInteger


class ThrottlingException(message: String) : Exception(message)

class RequestThrottler(private val maxConcurrentRequests: Int) {
    private val activeRequests = AtomicInteger(0)

    fun <T> executeWithThrottling(action: () -> T): T {
        if (activeRequests.incrementAndGet() > maxConcurrentRequests) {
            activeRequests.decrementAndGet()
            throw ThrottlingException("reached limit simultaneous reqests")
        }

        return try {
            action()
        } finally {
            activeRequests.decrementAndGet()
        }
    }
}

```



1. Для чего нужны аннотации @Produces и @Consumes при реализации REST-сервиса?\

`@Produces`  указывает на формат данных, который будет возвращаться клиенту

`@Consumes` указывает на формат данных, который клиент должен отправить на сервер

1. Какие методы являются идемпотентными с REST? В чем заключается свойство идемпотентности?

**Идемпотентность** означает, что несколько  идентичных запросов будут иметь такой же эффект, как и одиночный  запрос.

То есть в контексте REST это должны быть такие методы, которые либо не изменяют данные, либо не аккумулируются. Это могут быть `GET`, `PUT`, `DELETE` (второй раз удалить один объект не выйдет), `HEAD`, `OPTIONS`

1. Модифицируйте REST и SOAP сервисы так, чтобы для CREATE, DELETE,  UPDATE операций требовалась Basic аутентификация. Соответственно внесите такие же изменения в клиентские приложения. Login / Password для  простоты можно захардкодить.

В ktor (REST) это делается достаточно просто, при помощи добавления модуля `Authentication`:

```kotlin
install(Authentication)
```

На стороне клиента можно добавить авторизацию для каждого запроса с помощью такой обертки 

```kotlin
suspend fun makeAuthenticatedRequest() {
    val client = HttpClient()
    val authString = Base64.getEncoder().encodeToString("admin:admin".toByteArray())
    val response: HttpResponse = client.post("http://localhost:8080/api") {
        header("Authorization", "Basic $authString")
    }
}
```



Для SOAP  посложнее

```kotlin
class AuthenticationHandler : SOAPHandler<SOAPMessageContext> {
    override fun handleMessage(context: SOAPMessageContext): Boolean {
        if (context[MessageContext.MESSAGE_OUTBOUND_PROPERTY] as Boolean) {
            return true
        }
        
        val authHeader = headers.firstChild
        if (authHeader == null) {
            throw SOAPException("no auth header")
        }

        val authEncoded = authHeader.value
        val decodedBytes: ByteArray = Base64.getDecoder().decode(authEncoded)
        val authDecoded = String(decodedBytes)
        val credentials = authDecoded.split(":")

        val username = credentials[0]
        val password = credentials[1]

        if ("admin" == username && "admin" == password) {
            return true
        } else {
            return false
        }
    }

    override fun getHeaders(): Set<QName> = emptySet()
    override fun handleFault(context: SOAPMessageContext): Boolean = true
    override fun close(context: MessageContext) {}
}
```

и зарегистрировать этот обработчик в сервисе

```kotlin
fun main() {
    val endpoint = Endpoint.create(MySOAPService())
    val handlerChain = ArrayList<Handler>()
    handlerChain.add(AuthenticationHandler())
    endpoint.binding.handlerChain = handlerChain
    endpoint.publish("http://localhost:8080")
}
```

затем добавить аутентификацию в клиент 

```kotlin
val service = MySOAPServiceService()
val port = service.mySOAPServicePort
val requestContext = (port as BindingProvider).requestContext
requestContext[BindingProvider.USERNAME_PROPERTY] = "admin"
requestContext[BindingProvider.PASSWORD_PROPERTY] = "admin"

```



Код по этому вопросу также добавлен в репозиторий в сублиректории https://github.com/ideeockus/TechnologiesOfWebServices/additional_questions

1. В чем принципиальное различие при реализации обработки ошибок в REST и SOAP-сервисах с точки зрения разработки сервиса, а также с точки  зрения реализации клиента?
2. Предположим Вам нужно спроектировать public API для вашей системы.  Что вы выберете, REST или SOAP? Какие будут основные критерии для  выбора? Приведите ваши рассуждения по данному вопросу.
3. В каком случае вы предпочтете сервис-ориентированную архитектуру  вместо монолитной? Приведите ваши рассуждения по данному вопросу.
4. В чем заключается смысл использования UDDI-реестров в промышленных SOA-системах? Можно ли обойтись без реестров?
5. Для чего используются такие компоненты SOA, как ESB? Можно ли обойтись без них?