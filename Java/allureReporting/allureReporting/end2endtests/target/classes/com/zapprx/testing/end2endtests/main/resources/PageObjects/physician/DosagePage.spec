#Dosage
================================================================================
div_frequency				xpath		//input[@ng-model='model.dosagesData.frequency']/../div
inp_daysSupply				id		dosage-days-supply-mt-other
div_refills				xpath		//input[@ng-model='model.dosagesData.refills']/../div
btn_authorize&SavePres			id		next_button
btn_saveDraft				id		mid_button
div_finish&Review			id		done_modal
p_savePrescptnMsg			css		#confirmation-modal p
select_medicalRecord			id		uploadTypes
btn_selectFile				id		attach_document
link_fileName				xpath		//tr[@ng-repeat='record in medicalInfos']//a
inp_fileUpload				id		fileUpload_medical
btn_delete				xpath		//div[@ng-if='canDelete && canUpload']//button
div_delete				id		zp-modal-confirm-yes
p_delPresMsg				css		#zp-modal-success p
div_continue				id		zp-modal-success-btn
select_whoGrp				css		select[ng-model='prescription.who_group']
select_nyhaClass			css		select[ng-model='prescription.nyha_class']
inp_refills				id		dosage-refills
icon_cancel				css		#confirmation-modal i
inp_titRefill				id		rx-refills
inp_iniDos				id		dosage-initial_dosage
div_iniDos				xpath		//div[contains(@class,'zp-inputs-required')]//div[text()='Initiation dosage']
btn_date				css		.input-group-btn button
link_prv				css		.uib-daypicker th .pull-left
link_nxt				css		.uib-daypicker th .pull-right
txt_sig					id		rx-instructions-other-text
txt_directionsInst			id		rx-instructions-other-text
txt_othrInst				id		dosage-addnl-instructions
inp_sig					id		rx-auto-sig-checkbox
inp_dose				xpath		//section[@class='rx-dosage-section-main']//div[text()='Dose']/following-sibling::input
inp_otherWeek				css		input[ng-value='dosageManager.frequency']
inp_everyWeek				css		input[ng-value='dosageManager.frequency2']
inp_dirOther				css		input[ng-model='prescription.is_auto_sig']
hdr_details				id		dosage-collapse	
inp_RemodulinDose			xpath		//div[contains(text(),'$')]/following-sibling::span[contains(.,'%')]/input
inp_titrationSchedule			id		adempas-dosage-maint
txt_actremaDirInst			id		dosage-sig-text-entered-infused
inp_actremaMainDose			css		input[ng-model='dosage.selected.frequency']
div_dirctns				xpath		//div[@class='row']//div[text()='${fieldName}']	
inp_maintainDosage			id		dosage-dosage
select_quantity				id		dosage-days-supply-mt
inp_dosage				xpath		//div[text()='Dosage']/following-sibling::label[contains(.,'${value}')]/input
div_hdr					css		.zp-inputs-required>div
inp_calResponses			xpath		//div[@id='enrollment_questions']//li[contains(.,'${response}')]/input
txt_hypersens				css		.text_question>input
inp_calBlockerOption			xpath		//div[@id='enrollment_questions']/div//span[(contains(.,'${option}'))]/input
div_ccbStatement			xpath		//div[@id='enrollment_questions']/div//div[contains(text(),'${statement}')]
txt_responsesValue			xpath		//div[text()='${value}']/..//input
sel_drugAdm				xpath		//div[@id='enrollment-questions']//div[@class='col-md-6 ng-scope']/select
div_viewAddQusTitle			xpath		//div[@id='enrollment-questions']//div[@class="form-label"]
inp_dirOption				css		.ng-isolate-scope input[value='${option}']	
inp_dosageValues			xpath		//div[contains(text(),'$')]/../..//label[contains(.,'%')]/input
select_dosageValues			xpath		//label[text()='${fieldName}']/following-sibling::select	
h2_compassionateHeader                	css            	section[ng-if="medication.compassionate_use"] h2
inp_compassionateCheckbox             	css            	section[ng-if="medication.compassionate_use"] h2
select_frqncy				css	        select[ng-model='dosage.selected.frequency']
p_staticQuantity			id		dosage-days-supply-mt-static

#Flolan
select_diluent				css		select[ng-model='dosage.selected._diluents']
select_pump				css		select[ng-model='dosage.selected._pumps']

#Ventavis,Remodulin IV
select_refills				id		dosage-refills-select

#Uptravi
inp_rxRefills				id		rx-refills
div_strength				xpath		(//div[@class='col-md-12'])[1]

#opsumit
inp_opsumitQuantity			id		dosage-quantity

#Esbriet
inp_esbrietDosageQuantity		id		rx-dosage-quantity
inp_defIniTitOrder			id		dosage-initial-default
inp_quantity				id		rx-quantity
inp_esbrietStartNow			id		dosage-50242-0121-03-goal	

#PatientConsent
btn_capPatCon				id		capture-patient-consent-btn
li_drwShare                           	css            #sig-pad-share .drawIt
li_drwRems                           	css            	#sig-pad-rems .drawIt
canvas_signRems                       	css            #sig-pad-rems .pad
canvas_signShare			css		#sig-pad-share .pad
btn_savePatientConsent			id		patient-consent-modal-save
div_continueConsent			id		zp-modal-success-btn

#Adempas, Orenitram
inp_testInitialDose			xpath		//div[contains(text(),'Initial dose')]/parent::div//label[contains(.,'${option}')]/input
inp_testStrength			xpath		//label[contains(.,'${option}')]/input
select_initialDoseQuantity		id		dosage-days-supply-in
select_testFrqncy			id		dosage-frequency-select
inp_strengthRefill			css		rx-quantity[ng-if*='DosageType.MAINT'] input[id='dosage-refills']
inp_initialDoseRefill			css		rx-quantity[ng-if*='DosageType.INITIAL'] input[id='dosage-refills']

#Remodulin IV
inp_RemodulinDosage			xpath		//div[contains(text(),'$')]/following-sibling::div//label[contains(.,'%')]/input
label_RemodulinDosage			xpath		//div[contains(text(),'$')]/following-sibling::div//label[contains(.,'%')]

#Letairis, Tyvaso
inp_testDosage				xpath		//div[text()='Dosage']/following-sibling::div//label[contains(.,'${option}')]/input
p_sigInstruction			id		rx-sig
inp_dosageFrequency			id		dosage-frequency

#Flolan, Veletri, Epoprostenol
select_testDose				id		dosage-select
select_testDiluentOptions		css		#dosage-diluent option
select_DiluentDropdown			id		dosage-diluent
select_testPump				id		dosage-pump
================================================================================
