package org.example

import org.apache.juddi.api_v3.AccessPointType
import org.apache.juddi.v3.client.UDDIConstants
import org.apache.juddi.v3.client.config.UDDIClerk
import org.apache.juddi.v3.client.config.UDDIClient
import org.uddi.api_v3.*
import java.util.*




class UDDIClientApp(uddiServerUrl: String) {
    private val uddiClient: myUDDIClient

    init {
        val clerkManager = UDDIClient("META-INF/uddi.xml")
        val clerk = clerkManager.getClerk("default")

        this.uddiClient = myUDDIClient(uddiServerUrl, clerk)
    }

    fun run() {
        val scanner = Scanner(System.`in`)

        while (true) {
            println("Введите команду (register/find/exit):")
            val command = scanner.nextLine()

            if ("register" == command) {
                println("Введите имя сервиса:")
                val serviceName = scanner.nextLine()
                println("Введите URL сервиса:")
                val serviceUrl = scanner.nextLine()
                uddiClient.registerService(serviceName, serviceUrl)
            } else if ("find" == command) {
                println("Введите для поиска:")
                val serviceName = scanner.nextLine()

                val serviceUrl = uddiClient.findService(serviceName)
                if (serviceUrl != null) {
                    println("Найденный URL сервиса: $serviceUrl")
                } else {
                    println("Сервис не найден.")
                }
            } else if ("exit" == command) {
                println("Выход из программы.")
                break
            } else {
                println("Некорректная команда.")
            }
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val app = UDDIClientApp("http://localhost:8080")
            app.run()
        }
    }
}

class myUDDIClient(
    private val uddiServerUrl: String,
    private val clerk: UDDIClerk
) {
    fun registerService(serviceName: String?, serviceUrl: String?) {
        try {
            var serviceTModel: TModel = TModel()
            serviceTModel.setName(Name(serviceName, null))
            clerk.register(serviceTModel).tModel.first()

            val newService: BusinessService = BusinessService()
            newService.getName().add(Name(serviceName, null))

            val bindingTemplate = BindingTemplate()
            val accessPoint = AccessPoint()
            accessPoint.useType = AccessPointType.END_POINT.toString()
            accessPoint.value = "http://localhost:8080" // URL
            bindingTemplate.accessPoint = accessPoint

            newService.getBindingTemplates().getBindingTemplate().add(bindingTemplate)

            // Регистрация
            val registeredService: BusinessService = clerk.register(newService)
            System.out.println("Сервис зарегистрирован с ключом: " + registeredService.getServiceKey())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun findService(serviceName: String?): String? {
        try {
            val findService = FindService()
            findService.getName().add(Name(serviceName, null))
            val findQualifiers: FindQualifiers = FindQualifiers()
            findQualifiers.getFindQualifier().add(UDDIConstants.APPROXIMATE_MATCH)

            // Выполнение поиска
            val services: ServiceList = ServiceList()

            val serviceInfos = services.getServiceInfos().getServiceInfo()
            println("Найденные сервисы:")
            for (serviceInfo in serviceInfos) {
                System.out.println("Имя: " + serviceInfo.getName().get(0).getValue())
                System.out.println("Ключ сервиса: " + serviceInfo.getServiceKey())
            }
            return serviceInfos.firstOrNull()?.serviceKey
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            return null
        }
    }
}