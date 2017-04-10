#RxStatus
================================================================================
inp_search				css		.zp-searchbar-input
txt_patientName				xpath		//div[@id='rx-status-table']//td[contains(text(),'${patientName}')]
td_drug					xpath		//td[@ng-class="selectedColumn('name')"]
link_drugName				css		div[ng-if='externalUrl'] a
span_status				xpath		//td[contains(text(),'${patientName}')]/following-sibling::td[@class='status']//span
btn_searchIcon				css		.zp-searchbar-submit
div_loader				id		loader
td_patList				css		td[ng-class="tableSorter.getRowClassFromColumn('patient.actor.last_name')"]
btn_viewCompleteRx			xpath		//button[@id='rx-status-update-view-rx' and contains(text(),'${option}')]
td_paStatus                             xpath           //td[contains(text(),'${patientName}')]/following-sibling::td[contains(@ng-class,'filter_option.pa')]
i_paUrgntAlertIcon			xpath		//td[contains(text(),'${patientName}')]/following-sibling::td/i[contains(@class,'fa-exclamation-circle')]
li_openClose				id		doctor-nav-toggle
btn_updateRx				id		rx-status-update-rx

#pharmacy
txt_patNameMed				xpath		//div[@id='rx-status-table']//td[contains(text(),'$')]/following-sibling::td[contains(text(),'%')]
================================================================================
