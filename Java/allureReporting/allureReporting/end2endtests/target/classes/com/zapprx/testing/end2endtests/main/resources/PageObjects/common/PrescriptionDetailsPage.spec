#PrescriptionDetails
================================================================================
link_sendToEMR              		id      	rx-detail-btn-emr
div_priorAuth				id		phys-btn-pa
link_printSend				id		rx-detail-btn-print
form_radiobtn              		xpath     	//label[contains(.,'${formName}')]/input
form_print_btn 				id		rx-form-modal-print
link_expandAll				id		rx-detail-btn-expand
link_medication				xpath		//a[@href='#showDosage']
div_authorize				xpath		//div[@id='rx-detail-btn-group-actions']//button[contains(.,'Authorize Prescription')]
div_continue				css		div[ng-click='confirmSubmit()']
submit_modal                          	css             #submit-modal .modal-content
p_authSuccessMsg			css		#submit-modal p
span_status				id		rx-details-status
btn_complete				id		phys-btn-complete
div_connectionMsg			css		div[ng-show='corrections.notes']
inp_patientDOB				id		rx-detail-patient-dob
btn_submitCorrection			id		phys-btn-correction
div_continueCrrctn			css		#confirmation-modal div.button
link_markAsSent				id		rx-detail-btn-transmitted
div_transModal				css		#mark-rx-modal .modal-content
inp_transDate				id 		markRx
div_transSubmit				id		mark-btn
div_transContinue			css		#confirmation-modal .button
link_markAsComplete			id		rx-detail-btn-complete
inp_pharBenGrpNo			xpath		//h5[text()='${hdr}']/../following-sibling::div//input[contains(@ng-model,'group_number')]
select_potentialFemale			id		prescriber-auth-can-reprod
select_liverTest			id		prescriber-auth-liver
select_pregTestFemale			id		prescriber-auth-pregnancy-test
select_reproPotfemale			id		prescriber-auth-can-reprod
select_inpatientFemale			id              prescriber-auth-inpatient
td_presForm				xpath		//td[contains(@class,'description') and contains(.,'${formName}')]
tr_forms				xpath		//div[@class='table-scroll']//table[not(contains(@class,'hide'))]//tr[@class='ng-scope']
span_errrMsg				xpath		//div[@id='error-modal']//span[text()='${errorMsg}']
inp_titRefill				id		rx-refills
div_modal				id		mark-rx-modal
txt_addInst				id		dosage-addnl-instructions
link_drwSig				css		#sig-pad-detail .drawIt
canvas_sig				css		.signaturePad .pad
link_drug				id		rx-details-drug
p_emailId				css		.quick-info-box-subtitle
inp_refills				id		dosage-refills
sel_reason                            	css             select[ng-model='markedRx.reason']
span_sendToPharmacy			id		rx-details-transmitted
inp_other				css		input[ng-model='dosageManager.showOther']
txt_inst				id		dosage-sig-text-entered-infused
li_savedSig				css		.use-saved-sig a
inp_passwrd				id		doctor-signature-password
btn_verify				id		doctor-signature-modal-verify
inp_ccbOption				xpath		//div[@id='enrollment_questions']/div//span[(contains(.,'${option}'))]/input
txt_ccbTrial				css		.text_question>input
hdr_titleTyvaso				xpath		//div[@id='accordion']/div//h4[contains(.,'${response}')]
li_ccbResponses				xpath		//div[@id='enrollment_questions']/div//li[contains(.,'${response}')]
txt_responsesValue			xpath		//div[text()='${value}']/..//input
inp_dirOption				css		.ng-isolate-scope input[value='${option}']
inp_dosage				xpath		//label[contains(.,'${value}')]/input
txt_pharmacyName                      	css             tr[ng-repeat="form in formOptions.pharmacy"] td
dialog_pharForms                      	css             #print-form-options-modal .modal-content
btn_authorize				id		phys-btn-authorize
div_dosctorSigModal                   	css            	div[id='doctor-signature-modal']>div
inp_days                              	id           	dosage-days-supply-mt-other
span_paStatus                         	id             	rx-pa-status
link_markPASub&Completed             	xpath           //div[@id='rx-detail-actions-wrapper']//a[contains(.,'${text}')]
link_markPASub                        	id             	rx-detail-btn-pa-submitted
inp_subDate                           	css             .pa-approval-table input
div_sudModal                          	css             #approve-pa-modal .modal-content
div_subSubmit                         	css             #approve-pa-modal #mark-btn
span_priorMed                         	css             span[ng-show*='med.id']
btn_edit                              	id              phys-btn-edit
i_priorAuthCheck                      	xpath           //div[@class='rems-checklist']//li[contains(.,'${option}')]//i
btn_letterOfMedical                   	id              phys-btn-lon
div_print                             	css             #letter-modal div[ng-click="lomcVm.print()"]
span_reasonEnded 			xpath      	//div[@class='zapp-form']//span[contains(.,'${medicineName}')]/parent::td/following-sibling::td[contains(@ng-class,'reason')]/span
span_priorStatus			xpath		//div[@class='zapp-form']//span[contains(.,'${medicineName}')]/parent::td/following-sibling::td[contains(@ng-class,'status')]/span
span_faxStatus				id		rx-details-fax-status
inp_esbrietDosageQuantity		id		rx-dosage-quantity

#Patient Consent
link_patientConsent			id		rx-detail-patient-consent-link
btn_savePatientConsent			id		patient-consent-modal-save
div_continueConsent			id		patient-consent-modal-ok
canvas_signFemale			css		#sig-pad-rems .pad
canvas_signMale				css		#sig-pad-share .pad
li_drwFemale				css		#sig-pad-rems .drawIt
li_drwMale				css		#sig-pad-share .drawIt	

#Pharmacy
div_rqstCorrctn				id		corr-btn
inp_dob					css		input[ng-model='corrections.items.dob']
txt_crrctNote				css		#rx-detail-header-rx-col textarea
btn_saveCorrctn				xpath		//div[@id='rx-detail-header-rx-col']//button[text()='${buttonName}']
p_cnnctnRqstMsg				css		#corrections-modal p
div_cnnctnContinue			css		#corrections-modal .num-full
inp_patientFields			xpath		//div[@id='showPatient']//div[contains(.,'${fieldName}')]/following-sibling::input
span_calButton				css		#mark-rx-modal .input-group-btn button
btn_efax				id		rx-form-modal-fax
btn_printRxForm				id		rx-form-modal-print
radioBtn_selectRxFrom			css		.description input
btn_sendEFax				css		#efax-confirm-modal-fax
p_successText				css		#zp-modal-success p
btn_okay				id		zp-modal-success-btn
div_eFaxSendDestHeader			css		#efax-confirm-modal .form-group-label
h4_eFaxConfirmHeader			css		#efax-confirm-modal h4
div_eFaxSubject				xpath		//input[contains(@ng-model,'subject')]/preceding-sibling::div
btn_fulfill				id		fulfill-btn
p_fulfillsuccessMsg			css		#confirmation-modal p
div_fulfillContinue			css		#confirmation-modal .num-full
span_fulfilledDate			id		rx-details-filled

#Cancel Prescription
btn_cancel				id		phys-btn-cancel
p_cancelPresConfirmMsg			css		#zp-modal-confirm p
btn_cancelPresConfirmYes		id		zp-modal-confirm-yes
================================================================================
