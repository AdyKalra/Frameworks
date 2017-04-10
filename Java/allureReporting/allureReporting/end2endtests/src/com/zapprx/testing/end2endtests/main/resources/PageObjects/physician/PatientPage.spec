#Patients
================================================================================
inp_search				css		.zp-searchbar-input-wrapper input
txt_patientName				xpath		//div[@id='datagrid']//span[contains(text(),'${patientName}')]
txt_mrnNo				xpath		//div[@id='datagrid']//td[contains(text(),'${mrnNoAndDOB}')]
btn_searchIcon				css		.zp-searchbar-submit
div_prescribe				id		prescribe_modal
div_loader				id		loader
div_cmplteProfile			id		patient-modal-incomplete-prof-complete
div_home				id		home_modal
td_dob					xpath		//div[@id='datagrid']//span[contains(.,'${patientName}')]/../following-sibling::td[contains(@ng-class,'dob')]
td_patList				css		td[ng-class="tableSorter.getRowClassFromColumn('actor.last_name')"] span				

#PatientModal
div_viewProfile				id		patient-modal-view
div_patientPrescribe			id		patient-modal-prescribe
div_patientConsent			id		patient-modal-consent
hdrTxt_shareAuth			xpath		//span[contains(text(), 'Patient Consent')]
================================================================================
