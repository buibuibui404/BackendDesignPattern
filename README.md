### ISSUE1

* Before I fix this issue, around 800 MB memory will be used if you view or use reports things.
* After I fix this problem, it only cost around 450MB.
* To fix this problem, I create a package named flyWeight.
  * Interface I created:
    * ReportFlyWeight
    * ReportFlyWeightFactory
  * Class I created:
    * ReportFlyWeightFactoryImpl (implements ReportFlyWeightFactory)
    * ReportFlyWeightImpl (implements ReportFlyWeight)
  * Modification:
    * ReportImpl
      * I modified the consturct in the ReportImpl and creaete a private static ReportFlyWeightFactory in this class

### ISSUE2

* Before I fix this issue, if you want to create a new Type you need to create eight new classes which will create a big number of classes
* After I fix this problem, you only need create a new concrete TypeInfo for you new Type and use it with basic info, critical info and schudule info. It reduce the required number significantly.
* To fix this problem, I create a package named implementor in ordering. 
  * In the package, it includes
    * Interface:
      * BasicInfo
      * CriticalInfo
      * Schedule
      * TypeInfo
    * Class:
      * AuditOrderType (impkements TypeInfo)
      * BasicInfoImpl (impkements BasicInfo)
      * CriticalInfoImpl (impkements CriticalInfo)
      * FirstOrderType (impkements TypeInfo)
      * ScheduleImpl (impkements Schedule)
  * Also I create 
    * Class
      * OrderImpl (implements Order)
      * ScheduledOrderImpl (extends OrderImpl impkements ScheduledOrder)

### ISSUE3

* Before I fix this issue, the sendInvoice code in ContactHandler was really long and difficult to read
* After I fix this issue, it become much easier to read.
* To fix this issue
  * I create a package named streamline (using chain of responsiblity design pattern)
    * Interface
      * ContactHandlerNode
      * ContactRequest
    * Class
      * ContactRequestImpl (implements ContactRequest)
      * CarrierPigeonHandle (implements ContactHandlerNode)
      * EmailHandler (implements ContactHandlerNode)
      * InternalAccountingHandler (implements ContactHandlerNode)
      * MailHandler (implements ContactHandlerNode)
      * PhoneCallHandler (implements ContactHandlerNode)
      * SMSHandler (implements ContactHandlerNode)
  * Modification
    * ContactHandler
      * creat a new private static ContactHandlerNode variable 
      * static block to chain different node together
      * update the sendinvoce to use ContactHandlerNode

### ISSUE4

* Before I fix this issue, it cost a long time when you create a new client, because it will load all data from database which is time consuming
* After I fix this issue, the client can be create very fast and it only load data from database when it requires.
* To fix this issue:
  * Modification:
    * move load to method when the value is null then ask from database, if it's not null, don't load it.

### ISSUE5

* Before I fix this issue, it is very hard to compare two report if they are equal
* After I fix this issue, it can be compared just using .equal()
* To fix this issue:
  * Modification:
    * Add two method in ReportImpl
      * boolean equal(Object o)
      * int hashCode()

### ISSUE6

* Before I fix this issue, it will save the order to the database when it set a report, create a report and so on which is time consuming
* Aftre I fix this issue, it only save to the database when finalise and logout
* To fix this issue:
  * I create a package named orderUnit
    * Interface
      * UoWOrder
    * Class
      * UoWOrderImpl
  * Modification
    * private static UoWOrder variable
    * add it to method in FEAAFacade
      * Login
      * createOrder
      * getClient
      * finaliseOrder
      * logout
      * orderLineSet
      * getOrderLongDesc
      * getOrderShortDesc

### ISSUS7

* Before I fix this issue, you cannot do anything when you save your order
* After I fix this issue, you do other thing when you save your order
* To fix this issue:
  * Modification:
    * UoWOrder
      * extends Runable
    * UoWOrderImpl
      * add run method
      * add lock
    * Add a Thread in FEAAFacade