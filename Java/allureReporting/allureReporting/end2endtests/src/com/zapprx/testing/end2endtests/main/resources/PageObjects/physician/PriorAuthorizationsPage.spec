#PriorAuthorizations
================================================================================
span_diagnosis				xpath		//span[contains(.,'${patientFirstName}')]/../following-sibling::td[@class='diagnosis']/span
span_insurer				xpath		//span[contains(.,'${patientFirstName}')]/../following-sibling::td[@class='insurer']/span
div_tabState				xpath		//div[@id='tabs']//li[contains(text(),'${tabName}')]
span_patientName			xpath		//td[@class='patient-name']//span[contains(text(),'${patientName}')]
inp_patLastName				id		patient-last-name
txt_patientName				xpath		//div[@id='datagrid']//td[contains(text(),'${patientName}')]
txt_patNameMed				xpath		//div[@id='datagrid']//td[contains(text(),'$')]/following-sibling::td[contains(text(),'%')]
td_patList				css		td[ng-class*='patient.actor.last_name']
inp_txt					xpath		//input[@type='text']
btn_print				xpath		//div[contains(@class,'modal-footer')]//button[contains(.,'${btnName}')]
span_formTitle				css		.form-title
h1_paFormTitle                          css             #physicianApp .pa-header-title
inp_insuranceMemId                      id              insurance-member-id
span_paStatusOnForm                     css             .pa-form-template ul span[ng-class]
li_draw					css		.drawIt a
canvas_sig				css		.sigWrapper .pad
btn_saveSubmitReSub		        xpath		//div[contains(@class,'modal-footer')]//button[contains(.,'${buttonName}')]
h3_medicalRecords                       xpath           //div[contains(@class,'pa-universal-form')]//h3[contains(.,'${select}')]
btn_confirmYes                          id              zp-modal-confirm-yes
p_submitSuccess                         css             #zp-modal-success p
p_errorMsg				css		#zp-modal-error p
btn_okayContinue                        id              zp-modal-success-btn
btn_errorOkay				id		zp-modal-err-btn
a_viewRx                                css 	        .left-divider
a_pharViewRx				css		.pa-header-links a
td_paStatus                             xpath           //td[contains(text(),'${patientName}')]/following-sibling::td[@ng-if]
btn_submitMoreInfo                      css             button[ng-if*='inCorrectionMode']
btn_viewPADetails                       css             button[ng-click="viewPA(row.id)"]
btn_viewRxDetails                       css             button[ng-click="viewRx(row.prescription)"]
btn_requestMoreInfo                     css             button[ng-click="toggleCorrectionMode()"]
btn_review                              css             button[ng-click="reviewPA()"]
btn_updatePA                            css             button[ng-click="showPAUpdateModal()"]
btn_addNewFile                          id              attach_document
select_status                           css             div[pa-update] select
inp_subDate                             css             #pa-status-update-date-submitted input
inp_compDate                            css             #pa-status-update-date-completed input
btn_updateModal                         xpath           //div[@id='pharmacyApp']//div[@id='pa-update-status-modal']//button[contains(text(),'${Option}')]
btn_searchIcon				css		.zp-searchbar-submit
inp_search				css		.zp-searchbar-input-wrapper input
div_loader				id		loader
txt_textarea                            css             #pa-corrections-wrapper textarea
span_pharPAStatus                       id              rx-pa-status
btn_continue                            id              zp-modal-success-btn
h1_pharPAFormTitle                      css             #pharmacyApp .pa-header-title
h3_paTabs                               xpath           //div[@id='physicianApp']//h3[contains(.,'${select}')]
i_paTabsAngle	                        xpath           //div[@id='physicianApp']//h3[contains(.,'${select}')]/i
div_officePhone                        	xpath           //p[@id='practice-phone']/preceding-sibling::div
div_officeEmail                        	xpath           //p[@id='practice-email']/preceding-sibling::div
td_additionalInst                       xpath           //tr/td[contains(.,'Additional Instruction')]/following-sibling::td
btn_addPAFile                           css             .medical-upload-btn-pa #attach_document
inp_paUpload                            id              pa-medical-infos-upload
link_phyFileName                        css             #physicianApp .medical-upload-btn-pa a
link_filename                           css             #pharmacyApp .medical-upload-btn-pa a
btn_delete                              xpath           //button[contains(text(),'${deleteButton}')]
inp_priorMed                            css             .prior-meds-drug-name input
a_patientName                           css             a[ng-click="loadPatientProfile()"]
inp_edssScore                           css             .uqs-question-dir div[class='ng-scope'] input
select_genMedQues	                xpath           //div[contains(@class,'uqs-question-dir')]//span[contains(.,'${question}')]/parent::div/following-sibling::div//select
inp_pulmonaryArteryFields               xpath           //div[contains(@class,'uqs-question-dir')]//span[contains(.,'${question}')]/parent::div/following-sibling::div//input
select_vascularUnit			xpath		//div[contains(@class,'uqs-question-dir')]/div[contains(.,'${question}')]/following-sibling::div//select
inp_medRequest                          xpath           //div[contains(@class,'uqs-question-dir')]//span[contains(.,'${option}')]/input
inp_pharPAEDSSScore                     css             .uqs-question-dir div[ng-switch-when="number"] input
select_pharPAMedQuestions               xpath           //div[contains(@class,'uqs-question-dir')]//div[contains(.,"${question}")]/following-sibling::div//select/option[@selected="selected" and @label]
inp_pharPAMedRequest                    xpath           //div[contains(@class,'uqs-question-dir')]//input[contains(@class,'ng-not-empty')]/parent::span
div_pharPAClinicalQue                   css             .uqs-question-dir .form-label
td_pharReviewQue                        xpath           //div[contains(text(),'Clinical')]/parent::div/div//td[contains(@class,'color-gray-light-small')]
td_pharReviewValue                      xpath           //div[contains(text(),'Clinical')]/parent::div/div//td[contains(@class,'row-summary-value')]
select_presUploadType                   id              uploadTypes
btn_addPresAttachFile                   css             div[label="Prescription Attachments"] #attach_document
inp_presUpload                          id              rx-medical-infos-upload
a_presFileName                          css             .medicalRecords a
btn_presAttachDelete                    css             .tableCellHelp button
div_phyMedQues                          css             #uqs-medications .uqs-question-dir .form-label
h5_phygeneralques                       css             div[ng-if*='uqsVm.general'] .uqs-section-title
inp_reasonEnded 			xpath      	//div[@class='zapp-form']//span[contains(.,'${medicineName}')]/parent::td/following-sibling::td[contains(@ng-class,'reason')]/input
select_priorStatus			xpath		//div[@class='zapp-form']//span[contains(.,'${medicineName}')]/parent::td/following-sibling::td[contains(@ng-class,'status')]/select
txt_denialReason			id		pa-status-update-denied-reason
btn_appeal				css		.pa-btn-appeal
btn_reviewPrint				css		button[ng-click="printPASummary()"]
span_priorAndCurrentMed                 css             span[ng-show="isDisabled || med.id"]
p_primaryDiagnosis			id		rx-diagnosis-primary-name
p_newDiagnosis				id		rx-diagnosis-primary-new
p_otherDiagnosis			id		rx-diagnosis-primary-other-diagnosis
inp_paUrgntCheckbox			css		.pa-urgent-text input
span_paUrgentMrked			css		.pa-urgent-text span[ng-show="!stateManager.canEdit"]
i_paUrgntAlertIcon			xpath		//td[contains(text(),'${patientName}')]/following-sibling::td[@ng-if]/i[contains(@class,'fa-exclamation-circle')]
span_additionalInfo			css		#additional-information span
txt_additionalInfo			css		#additional-information textarea
td_additionalInfoHeader			xpath		//div[@id='pa-review-modal']//div[contains(text(),'Clinical')]/parent::div//td[contains(text(),'${additionalInfo}')]
td_additionalInfoText			xpath		//div[@id='pa-review-modal']//div[contains(text(),'Clinical')]/parent::div//td[contains(text(),'${additionalInfo}')]/following-sibling::td
label_additionalInfo			css		.overflow-additional-info label 
txt_printAdditionalInfo			css		.overflow-additional-info textarea
p_dosages				id		rx-dosage
p_rxInstruction				id		rx-instructions
img_physicianSig			css		div[ng-show="!signatureState.drawNewSig"]

#pharmacy
btn_cancelMoreInfo			css		button[ng-click="confirmCorrectionsCancellation()"]
p_cancelMoreInfoCnfrmTxt		css		#zp-modal-confirm p
btn_priorAuth				id		phys-btn-pa
btn_PAListUpdate			css		.pa-status-update button[type="submit"]
================================================================================
