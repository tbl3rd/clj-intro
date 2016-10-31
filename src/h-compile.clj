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
 [:vmext:VimServer {:name "vc1-name"}
  [:Description "Got me ..."]
  [:vmext:Username "vc1-user"]
  [:vmext:Password "vc1-pass"]
  [:vmext:Url "https://127.0.0.1"]
  [:vmext:IsEnabled true]]
 [:vmext:ShieldManager {:name "vsm1-name"}
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

{[247711382 128745107 615585803] #{ 7 20 19 81 91}
 [724475530 209311813 596076018] #{47 35  3 49 42}
 [398807783 687106718 953042208] #{ 5 51 98 42 73}
 [ 49338370 359939853 906095485] #{61 79 22 63 36}
 [571521555 682416481 785036962] #{86 51 65  1 89}
 [174818746 239856058 437406230] #{ 6 22 50 75 36}
 [535936573 885819910 472347815] #{74 29 31 64 66}
 [ 70989909 376305819 883810783] #{26 68 78  9 52} ; ...
 }

{::one-ofs [#{:hostgroup_name :host_name}
            #{:dependent_host_name :dependent_hostgroup_name}
            #{:dependent_service_description :dependent_servicegroup_name}]
 :dependency_period              [false fmt-one timeperiod?       ]
 :dependent_host_name            [false fmt-mul hosts?            ]
 :dependent_hostgroup_name       [false fmt-mul hostgroups?       ]
 :dependent_service_description  [false fmt-mul services?         ]
 :dependent_servicegroup_name    [false fmt-mul servicegroups?    ]
 :execution_failure_criteria     [false fmt-set fc?               ]
 :host_name                      [false fmt-mul hosts?            ]
 :hostgroup_name                 [false fmt-mul hostgroups?       ]
 :inherits_parent                [false fmt-tof boolean?          ]
 :notification_failure_criteria  [false fmt-set fc?               ]
 :service_description            [false fmt-mul services?         ]
 :servicegroup_name              [false fmt-mul servicegroups?    ]
 :use                            [false fmt-use servicedependency?]}
