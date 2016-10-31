["Clojure", 0 :to fn?]                  ; an introduction

[4 [2 [0 1] [3]] [7 [5 6] [8 9]]]

(comment
  [             4
   [     2
    [0 1] [3]              ]
   [                7
               [5 6] [8 9]]]
  )

[:vmext:RegisterVimServerParams
 [:vmext:VimServer :name "dvc1-name"
  [:Description "Got me ..."]
  [:vmext:Username "lyonst"]
  [:vmext:Password "password"]
  [:vmext:Url "https://127.0.0.1"]
  [:vmext:IsEnabled true]]
 [:vmext:ShieldManager :name "vsm1"
  [:vmext:Username "vsm1-user"]
  [:vmext:Password "vsm1-pass"]
  [:vmext:Url "https://127.0.0.1"]]]

{"admin.catalog"  "application/vnd.vmware.admin.catalog+xml"
 "vcloud.catalog" "application/vnd.vmware.vcloud.catalog+xml"
 "admin.vdc"      "application/vnd.vmware.admin.vdc+xml"
 "vcloud.vdc"     "application/vnd.vmware.vcloud.vdc+xml"}

{:delete ["remove" "remove:force"],
 :get ["alternate" "blockingTask" "catalogItem" "down"
       "down:aclRules" "down:apiDefinitions" "down:apiFilters"
       "down:apidefinitions" "down:fileDescriptors" "down:files"
       "down:resourceClassActions" "down:resourceClasses"
       "down:serviceLinks" "down:serviceResources" "down:services"
       "download:default" "download:identity"
       "edgeGateways" "entity" "entityResolver"
       "firstPage" "lastPage" "nextPage" "previousPage"
       "orgVdcNetworks" "ovf" "resourcePoolVmList" "rights"
       "screen:acquireTicket" "screen:thumbnail"
       "shadowVms" "storageProfile"
       "task" "task:owner" "up" "vSphereWebClientUrl"],
 :not-applicable ["copy" "download:alternate" "move"
                  "ova" "upload:alternate"],
 :post ["abort" "add" "answer" "authorization:check"
        "bundles:cleanup" "certificate:reset" "certificate:update"
        "checkCompliance" "consolidate" "controlAccess"
        "customizeAtNextPowerOn"
        "deploy" "disable" "discardState" "disk:attach" "disk:detach"
        "down:extensibility"
        "edgeGateway:reapplyServices" "edgeGateway:redeploy"
        "edgeGateway:syncSyslogSettings" "edgeGateway:upgrade" "enable"
        "enterMaintenanceMode" "event:create" "exitMaintenanceMode"
        "fail" "installVmwareTools" "instantiate"
        "keystore:reset" "keystore:update" "keytab:reset" "keytab:update"
        "media:ejectMedia" "media:insertMedia" "merge" "migrateVms"
        "power:powerOff" "power:powerOn" "power:reboot" "power:reset"
        "power:shutdown" "power:suspend"
        "publish" "publishToExternalOrganizations"
        "recompose" "reconfigureVm" "reconnect" "refreshStorageProfiles"
        "refreshVirtualCenter" "register" "reloadFromVc" "relocate"
        "repair" "resume" "rights:cleanup"
        "snapshot:create" "snapshot:removeAll" "snapshot:revertToCurrent"
        "subscribeToExternalCatalog" "sync" "syncSyslogSettings"
        "takeOwnership" "task:cancel" "task:create" "truststore:reset"
        "undeploy" "unlock" "unregister"
        "update:resourcePools" "updateProgress" "upgrade"],
 :put ["bundle:upload" "edgeGateway:configureServices" "edit"
       "truststore:update" "upload:default"]}

[:html5
 [:head
  [:title "Home | Compojure Docs"]]
 [:body
  [:div {:class "container-fluid"}
   [:div {:class "row-fluid"}
    [:div {:class "span2 menu"}]
    [:div {:class "span10 content"}
     [:h1 "Compojure Docs"]
     [:ul
      [:li
       [:a {:href "/getting-started"} "Getting Started"]]
      [:li
       [:a {:href "/routes-in-detail"} "Routes in Detail"]]
      [:li
       [:a {:href "/nesting-routes"} "Nesting Routes"]]
      [:li
       [:a {:href "/api-documentation"} "API Documentation"]]
      [:li
       [:a {:href "/example-project"} "Example Project"]]
      [:li
       [:a {:href "/emacs-indentation"} "Emacs Indentation"]]]]]]]]

{\b
 {\e {\t {:$ "bet"}},
  \a
  {\n {\d {:$ "band"}, \a {\n {\a {:$ "banana"}}}},
   \t {:$ "bat", \s {:$ "bats"}, \c {\h {:$ "batch"}}}}}}

(comment
  {\b
   {\e {\t {:$ "bet"}},
    \a
    {\n {\d {:$ "band"},
         \a {\n {\a {:$ "banana"}}}},
     \t {:$ "bat",
         \s {:$ "bats"},
         \c {\h {:$ "batch"}}}}}}
  )
