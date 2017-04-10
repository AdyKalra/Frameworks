#HomePage
================================================================================
link_register				xpath		//button[contains(text(),'Register')]
link_prescribe				xpath		//button[contains(text(),'Prescribe')]
li_patientSigned			id		patientSigned
li_readyForPhar				id		actions
button_markAsSent			xpath		//td[contains(text(),'${patientName}')]/following-sibling::td//button[contains(@ng-click,'transmitted')]
inp_date				id		markRx
span_calButton				css		#mark-rx-modal .input-group-btn
div_close				xpath		//div[@id='confirmation-modal']//div[contains(text(),'Close')] 
td_patientName				xpath		//div[@id='actions']//td[contains(.,'${patientName}')]
tr_patientName                          xpath           //div[@id='patientSigned']//td[contains(.,'${patientName}')]/parent::tr

#Patient
link_notification			xpath		//h3[@title='RECENT NOTIFICATIONS']/../following-sibling::div//strong[text()='${medicationName}']
div_notificationMsg			css		.home-notification-msg .notification-message-text

#admin
btn_addNewPrac				css		button[ng-click='addPractice()']
li_pharmacies				id		home-tab-pharmacies
btn_addNewPharmacy			css		button[ng-click='addPharmacy()']
================================================================================
