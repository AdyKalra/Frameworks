#PriorAuth
================================================================================
inp_form				xpath		//input[@name='form_choice']
btn_selForm				css		button[ng-click='submitFormChoice()']
btn_save				xpath		//button[contains(text(),'${buttonName}')]
btn_reviewPres				xpath		//div[contains(text(),'${buttonName}')]
canvas_sign				classname	pad
div_submit				css		div[ng-click='authorize()']
div_reviwPresModal			xpath		//div[@class='modal-footer']//div[contains(.,'${buttonName}')]
modal_drawSign				xpath		//*[contains(@class,'sigWrapper') and contains(@style,'block')]
link_clearSign				css		a[href='#clear']
inp_firstName				xpath		//div[@id='patient_fname']//div/input
inp_lastName				xpath		//div[@id='patient_lname']//div/input
p_errorMsg				classname	error
div_drugState				xpath		//div[@id='invalid-modal']//b[contains(.,'Drug Start Date')]
================================================================================
