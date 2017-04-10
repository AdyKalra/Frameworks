#Patient Registration
================================================================================
icon 					xpath		//i[contains(@class,'orange')]
inp_requiredField			xpath		//input[@id='${fieldName}']/preceding-sibling::div
select_requiredField			xpath		//select[@id='${fieldName}']/preceding-sibling::div

#New Patient Registration
inp_firstName				id		quick-register-firstname
inp_lastName				id		quick-register-lastname
inp_dob					id		dob
select_gender				id		quick-register-gender
btn_savePatient				id		quick-register-save

#General Information
inp_emrId				id		emr_id
inp_loginEmail				id		email_
inp_password				id		password
inp_confirmpassword			id		confirmpassword
select_heightUnits			id		height_units
inp_height				id		height
select_weightUnits			id		weight_units
inp_weight				id		weight
inp_address				id		street1_0
inp_city				id		city_0
select_state				id		state_0
inp_zip					id		postal_code_0
inp_email				id		email_0
inp_mainPhone				id		phone_0
inp_contactName				id		emergencyname
inp_relationship			id		emergencyrelationship
inp_phone				id		emergencyphone
btn_next				xpath		//button[@id='next_button' and contains(text(),'${buttonName}')]
btn_searchPatients			css		#registrationForm .button
hdr_searchResults			css		.zapp-form .modal-body>h3
icon_close				xpath           //div[@id='emr-modal']//div[@class='modal-header']/i
p_emailErroMsg				css		#registerError-modal p[class='ng-binding']
span_chckPatNoEmail			id		noUserCheckbox
inp_mrn					id              emr_id
p_invalidEmailMsg			css		#zp-modal-error p
inp_patContactName                    id              name_name0

#Insurance Information
link_step1				id		previous_button
inp_insrncName				xpath		//h2[text()='$']/following-sibling::div[contains(.,'%')]//div[contains(@class,'form-label')]/following-sibling::div/input
inp_insField				xpath		//h2[text()='$']/following-sibling::div/div/div/div[contains(.,'%')]/following-sibling::input
select_insField				xpath		//h2[text()='$']/following-sibling::div//div[contains(.,'%')]/following-sibling::select
btn_addInsurance			id		register-insurance-add-info
hdr_txt					xpath		//form[@id='insuranceForm']//h2[text()='${txt}']
btn_removeInsurancePBM			xpath		//form[@id='insuranceForm']//h2[text()='${txt}']/preceding-sibling::button
btn_removeInsurance                   css             button[ng-click="deleteInsuranceInfo(insurance)"]
btn_addPharmacyBenefit			id 		register-insurance-add-pbm

#Medical History
select_patient				id		is_inpatient
select_diabetic				id		is_diabetic
select_bloodPressure			id		has_high_blood_pressure
select_latexAllergy	             	id		has_latex_allergy
btn_submit				id		submit_button
inp_knownDrugAllergy			id		drug_allergies

#Clinical Profile
select_indication                     css            select[ng-model='cpStateManager.disease']
select_diagnosis                      id             diagnosis-primary
select_otherDetails                   id             diagnosis-primary-other
span_addAdditionalMed                 id             clinical-medication-new
select_medication                     css            select[ng-model="med.selectedTherapy"]
i_medRemove                           css            .btn-med-remove i
p_removeMedConfirm                    css            #zp-modal-confirm p
btn_modalYes                          id             zp-modal-confirm-yes
h5_generalques                        css            .uqs-section-toggle
inp_edssScore				css		#uqs-general div[class='ng-scope'] input
btn_addAddtnlSymptom			css		button[ng-click='renderSymptomModal(true)']
select_symptomName			id		symptom-name
inp_symStartDate			css		div[ng-model="symptomVm.selectedSymptom.start_date"] input
inp_symEndDate				css		div[ng-model="symptomVm.selectedSymptom.end_date"] input
btn_symSave				id		patient-symptom-modal-save
btn_okay				id		zp-modal-success-btn
td_symptomName				xpath		//div[contains(@class,'clinical-symptoms')]//tr[@class='ng-scope']/td[1]
btn_symDelete				id		patient-symptom-modal-delete
btn_symYes				id		patient-symptom-modal-delete-yes

#Registration Success
div_completeProfile			id 		quick-register-complete-profile
div_home				id		home_modal
p_successMsg				css		#registration-success-modal p[class]
div_patConsent				id		quick-register-capture-consent
div_patntList				id		quick-register-view-profile
================================================================================
