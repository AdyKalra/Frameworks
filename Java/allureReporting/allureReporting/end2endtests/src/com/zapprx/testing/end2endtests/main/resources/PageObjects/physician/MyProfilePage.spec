#My Profile
================================================================================
btn_saveNewSig				css		div[ng-show='doctor.signature.point_set'] button
inp_address				id		street1
inp_city				id		city
inp_state				id		state
inp_zip					id		zip
canvas_pad				css		.pad
btn_saveProfile				id		save_button
btn_closeOkay				id		zp-modal-success-btn
img_sign				css		.saved-sig-box>img
inp_userName				css		.signatory-search-input
txt_userName				xpath		//div[@id='signatory-wrapper']//td[contains(text(),'${firstName}')]
td_userList				css		td[ng-class*='actor.last_name']
select_markSignatory			css		#add-signatory-modal select
inp_expDate				id		signatory-exp-date
btn_update				css		button[class='button']
p_signatorySuccess			css		#zp-modal-success p
td_signatoryStatus			xpath		//div[@id='signatory-wrapper']//td[contains(text(),'${firstName}')]/preceding-sibling::td/i[@ng-if="doctor.is_signatory"]
================================================================================
