#PatientProfile
================================================================================
span_tab				xpath		//li/span[contains(text(),'${tabName}')]
inp_drugAllergy				id		drug_allergies
inp_inPatient				id		is-inpatient-disabled
inp_diabetic				id		is-diabetic-disabled
inp_hasBloodPressure			id		has-high-bp-disabled
btn_edit				id		edit_button
btn_save				id		save_button
inp_address				css		input[ng-model='contact.street1']
td_medicationName			xpath		//tr[@ng-click="loadPA(paCurrent)"]/td[text()='${medicationName}']
btn_selectFile 				id		attach_document
select_upload				id		uploadTypes
inp_fileUpload				id		fileUpload_medical
link_fileName				xpath		//div[@class='zapp-form']//tr[@ng-repeat='record in medicalInfos']//a
btn_remove				xpath		//div[@ng-if='canDelete && canUpload']//button
div_remove				id		zp-modal-confirm-yes
div_continue				id 		zp-modal-success-btn
span_addMedication			id		clinical-medication-new
inp_datePres				id		medStartDate0
td_newMedication			xpath		//div[@class='zapp-form']//span[text()='${medicationName}']
inp_insuranceName			xpath		//span[contains(text(),'$')]/parent::h4/following-sibling::div//div[text()='%']/..//input
inp_weight				name		weight
inp_height				name		height
select_heightUnits			name		height_units
select_weightUnits			name		weight_units
inp_disWeight				xpath		//select[@name='weight_units']/following-sibling::input
inp_disHeight				xpath		//select[@name='height_units']/following-sibling::input
link_medical				id		dosage-collapse-medical
link_labs				id		dosage-collapse-labs
link_clincalProfile			id		dosage-collapse-clinical-profile
select_indication                       css             select[ng-model='cpStateManager.disease']
select_diagnosis                        id              diagnosis-primary
select_otherDetails                     id              diagnosis-primary-other
select_med                              css             select[ng-model='med.selectedTherapy'] 
link_presDetails                        id              dosage-collapse-rx-history
div_medicalInfo                         xpath           //div[@id='profile-medical']//div[text()='${option}']
div_formHeader                          xpath           //div[@class='zapp-form']/h4[text()='${header}']
td_reason                               css             td[ng-class="{'reason': !isDisabled, 'med-reason-new': !med.id}"] input
select_status                           css             select[ng-model="med.status"]
td_priorReasonTxt                       css             td[ng-class="{'reason': !isDisabled, 'med-reason-new': !med.id}"] span
td_priorStatusTxt                       css             td[ng-class="{'status': !isDisabled}"] span
btn_addPharBenefit                      id              btn-add-pharmacy-benefits
btn_addInsurance                        id              btn-add-insurance
btn_removeInsuracePBM              	xpath           //div[contains(@class,'insurance-panel')]//span[contains(text(),'${insuranceName}')]/parent::h4/preceding-sibling::button
h4_insurancePBMHeader	                xpath          	//button[contains(@class, 'delete_info_button')]/following-sibling::h4/span[contains(text(),'${insuranceName}')]
btn_delete                              id              zp-modal-confirm-yes
span_medications                        css             span[ng-show="isDisabled || med.id"]
i_medRemove                             css             .btn-med-remove i
p_removeMedConfirm                      css             #zp-modal-confirm p
btn_modalYes                            xpath           //div[@id='zp-modal-confirm']//p[contains(.,'${removeMsg}')]/parent::div/following-sibling::div//button[@id='zp-modal-confirm-yes']
section_priorAuthMsg                    css             .patient-profile-panel section[ng-show="!paCurrent "] p
h5_generalques                          css             div[ng-if*='uqsVm.general'] .uqs-section-toggle
h5_medSpecificQues                      css             div[ng-if*='uqsVm.medications'] .uqs-section-toggle
div_medQues                             xpath           //div[@id='uqs-medications']//h5[contains(.,'${medicationName}')]/parent::div//div[contains(@class,'form-label')]
inp_contactName				css		input[ng-model="contact.name"]
btn_cancel				id		cancel_button

#Additional Symptoms
btn_addAddtnlSymptom			css		button[ng-click='renderSymptomModal(true)']
select_symptomName			id		symptom-name
inp_symStartDate			css		div[ng-model="symptomVm.selectedSymptom.start_date"] input
inp_symEndDate				css		div[ng-model="symptomVm.selectedSymptom.end_date"] input
btn_symSave				id		patient-symptom-modal-save
btn_okay				id		zp-modal-success-btn
td_symptomName				xpath		//tr[@ng-click="renderSymptomModal(true, patientSymptom)"]/td[text()='${option}']
btn_symDelete				id		patient-symptom-modal-delete
btn_symYes				id		patient-symptom-modal-delete-yes
================================================================================
