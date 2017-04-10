#PatientDetails
================================================================================
inp_requiredField			xpath		//input[@id='${fieldName}']/preceding-sibling::div
select_requiredField			xpath		//select[@id='${fieldName}']/preceding-sibling::div
update_confirmation        		id  		update-confirmation-yes
h2_compassionateHeader                	css             section[ng-if="medication.compassionate_use"] h2
inp_compassionateCheckbox             	css             section[ng-if="medication.compassionate_use"] h2
btn_diagnosis				id		next_button

#Physician Information
select_presProvider			id		select_provider

#Patient Identification
inp_firstName				id		first_name
inp_lastName				id		last_name
inp_dob					id		dob
inp_userName				id		username
inp_mrn					id              emr_id
inp_ssn                               	id              ssn

#Patient Statistics
select_gender				id		gender
inp_height				id		height
inp_weight				id		weight
select_heightUnits			id		height_units
select_weightUnits			id		weight_units
inp_drugAllergy				id		drug_allergies
select_inPatient			id		is_inpatient
select_diabetic				id		is_diabetic
select_bloodPressure			id		has_high_blood_pressure
select_latexAllergy             	id		has_latex_allergy

#Contact Information
select_type                           	id		contact-type
inp_address				id		contact-street1
inp_city				id		contact-city
select_state				id		contact-state
inp_zip					id		contact-postal-code
inp_email				id		email
inp_phone				id		contact-hone
option_state                          	css            	#state option[label][selected]
btn_addNewAdd                         	xpath          	//button[contains(@ng-click,'renderContactInfoModal()') and not(@ng-show)]
btn_contactSave                       	id             	rx-contact-info-modal-save

#Insurance Information
inp_insuranceName			css		input[id='insurance-insurer-name-']
inp_insuranceState			id		insurer_state
select_insurerState                   	id             	insurance-state
inp_insurancePhone			id		insurance-phone
inp_insuranceMemberId			id		insurance-member-id
inp_insuranceGroupNo			id		insurance-group-number
inp_rxBin				id		insurer_bin_number
inp_rxPCN				id		insurer_pcn_number
inp_insuredName				id		insured_name
select_insuredRelation			id		insurance-insured-relationship
inp_insuranceEmployer			id		insurance-employer
select_primaryInsurance               	id             	insurance-select-primary
btn_addNewIns                         	css            	button[ng-click="renderInsuranceInfoModal()"]
btn_insuranceSave                     	id             	rx-insurance-info-modal-save

#Emergency Contact Information
inp_emrgncyName				id		emergency_name
inp_emrgncyRel				id		emergency_relationship
inp_emrgncyPhn				id             	emergency_phone


#Pharmacy Benefits And Secondary Insurance	
inp_pharBenGrpNo			xpath		//h2[contains(.,'${headerText}')]/following-sibling::div//input[@id='insurer_group_num']
span_groupNo				xpath		//strong[contains(.,'$')]/parent::p/span[contains(.,'%')]
radioBtn_secondry			xpath		//strong[contains(.,'${insuranceName}')]/parent::p/following-sibling::div/input[contains(@ng-model,'radioModel.insurance.secondary')]
p_insuranceErrMsg			css 		#zp-modal-error p

#Preferred Speciality Pharmacy
select_pharmacy				id		pharmacy
option_pharmacy                       	css            	option[label='${option}']

#Shipping Information
select_ship				css		#patientInfo select[ng-model="selected.shippingSource"]
select_selectedShipOption             	css            	#patientInfo select[ng-model="selected.shippingSource"] option[selected]
inp_shipDetails				css		div[ng-if*='ShippingSource.PATIENT_HOME'] p[class='ng-binding']
inp_shipZip                           	id             	shipping_postal_code
inp_shipEmail                         	css            	div[ng-if*='ShippingSource.PATIENT_HOME'] p[class='ng-binding'] span[ng-show='contact.email']
inp_shipPhone                         	css            	div[ng-if*='ShippingSource.PATIENT_HOME'] p[class='ng-binding'] span[ng-show='contact.phone']

btn_previous				id		previous_button	


#PatientConsentForHub
inp_patientConsentYes    		id     		patient-consent-hub-yes
inp_patientConsentNo     		id     		patient-consent-hub-yes

#PatientConsent
btn_capPatCon				id		capture-patient-consent-btn
li_drwHIPAA                           	css            	#sig-pad-hipaa .drawIt
li_drwPatService&Share                	xpath          	//h2[contains(.,'${authType}')]/following-sibling::signature-pad//li[@class='drawIt']
li_drwRems                            	css            	#sig-pad-rems .drawIt
canvas_signRems                       	css            	#sig-pad-rems .pad
canvas_signHipaa			css		#sig-pad-hipaa .pad
canvas_signPatService&Share           	xpath          	//h2[contains(.,'${authType}')]/following-sibling::signature-pad//canvas[@class='pad']
btn_savePatientConsent			id		patient-consent-modal-save
div_continue&Okay			id		zp-modal-success-btn
p_errorMsgShare                       	xpath          	//signature-pad[@id='sig-pad-share']/p[1]
p_errorMsgRems                        	xpath          	//signature-pad[@id='sig-pad-rems']/p[1]
p_successMsg                          	css            	#zp-modal-success p
div_rems				css		#form-wrapper-rems div[auth='REMS']       
================================================================================
