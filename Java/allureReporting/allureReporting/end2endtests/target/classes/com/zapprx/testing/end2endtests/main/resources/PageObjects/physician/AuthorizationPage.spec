#Authorization
================================================================================
#Physician
btn_submit				id		submit_button
btn_priorAuth           		id		pa_modal
div_review				id		done_modal
btn_previous				id		patient-consent-btn-previous
btn_save				id		patient-consent-btn-submit
btn_continue				id		zp-modal-success-btn
select_femalePatient			id		prescriber-auth-can-reprod
select_pregTest				id		prescriber-auth-pregnancy-test
select_liverTest			id		prescriber-auth-liver
select_potTest				id		prescriber-auth-reprod-status
li_drwSig                               css		li[class='drawIt'] a
link_drwSig                             css             #sig-pad-flow .drawIt a
link_femaleDrwSig			css		#sig-pad-rems li[class='drawIt'] a
canvas_femalesig			css		#sig-pad-rems .pad
link_maleDrwSig				css		#sig-pad-share li[class='drawIt'] a
canvas_maleSig				css		#sig-pad-share .pad
p_signErrorMsg				css		#sig-pad-flow .error
inp_name				id		name
p_successMsg				css		#confirmation-success-modal p
select_inPatFac          		id		prescriber-auth-inpatient
li_savedSig				css		.use-saved-sig a
inp_passwrd				id		doctor-signature-password
btn_verify				id		doctor-signature-modal-verify
div_msg					css		.well
canvas_sig				css		#sig-pad-flow .pad
img_masterSig				css		div[ng-if="masterDoctor.signature"] img
span_masterSigMsg			css		span[ng-show="masterDoctor.signature"]

#PatientConsent
btn_capPatCon				id		capture-patient-consent-btn
li_drwShare                             css             #sig-pad-share .drawIt
li_drwRems                              css             #sig-pad-rems .drawIt
canvas_signRems                         css             #sig-pad-rems .pad
canvas_signShare			css		#sig-pad-share .pad
btn_savePatientConsent			id		patient-consent-modal-save
div_continueConsent			id		zp-modal-success-btn
================================================================================
