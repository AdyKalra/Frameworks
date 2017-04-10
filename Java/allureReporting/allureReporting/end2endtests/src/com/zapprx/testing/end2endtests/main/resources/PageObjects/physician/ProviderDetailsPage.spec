#ProviderDetails
================================================================================
inp_requiredField			xpath		//input[@id='${fieldName}']/preceding-sibling::div
select_requiredField			xpath		//select[@id='${fieldName}']/preceding-sibling::div

#Physician Information
slct_provider				id		select_provider
inp_firstName				id		doctor_first_name
inp_lastName				id		doctor_last_name
inp_state				id		doctor_state
inp_deaNo				id		doctor_dea_number
inp_npiNo				id		doctor_npi_number

#Contact Information
inp_address				id		practive_street1
inp_city				id		practice_city
select_state				id		practice_state
inp_zip					id		practice_zip
================================================================================
