#Diagnosis Page
================================================================================
select_requiredField			xpath		//select[@id='${fieldName}']/preceding-sibling::div
select_primaryDiagnosis			id		primary_diagnosis
select_newDiagnosis			id		primary_diagnosis_new
btn_dosage				id		next_button
select_otherDetails			id		primary_diagnosis_other_details
option_newDiagnosis                     css             select[id='primary_diagnosis_new'] option[label][selected="selected"]
li_additionalDia                        xpath           //ul[@id="secondary_diagnoses"]/li[contains(.,'${diagnosisName}')]/input
hdr_details				id		dosage-collapse
a_patient                               id              previous_button

#PatientConsent
btn_capPatCon				id		capture-patient-consent-btn
li_drwShare                             css             #sig-pad-share .drawIt
li_drwRems                              css             #sig-pad-rems .drawIt
canvas_signRems                         css             #sig-pad-rems .pad
canvas_signShare			css		#sig-pad-share .pad
btn_savePatientConsent			id		patient-consent-modal-save
div_continueConsent			id		zp-modal-success-btn

#AddAdditionalMedication
sapn_addAdditionalMed                   id              clinical-medication-new
select_medication                       css             select[ng-model="med.selectedTherapy"]
i_medRemove                             css             .btn-med-remove i
p_removeMedConfirm                      css             #zp-modal-confirm p
btn_modalYes                            id              zp-modal-confirm-yes
additional_diagnosis                    css             a[ng-click="additionalDiagnoses.expanded = !additionalDiagnoses.expanded"]
span_priorMed                           css             span[ng-show*='med.id']
inp_reasonEnded                         css             .choose-medications .additions input[ng-model='med.reason']
h5_generalques                          css             .uqs-section-toggle
================================================================================
