#NewPharmacyPage
====================================================================================
hdr_newPharmacy				css		div[title='New Pharmacy'] h2
inp_name				id		name
inp_street				id		street1
inp_city				id		city
select_state				id		state
inp_zip					id		zip
btn_savePharmacyProfile			id		practice-profile-edit-save
td_pharName				xpath		//table[contains(@class,'table-striped')]/tbody/tr[not(contains(@class,'ng-hide'))]/td[contains(@ng-class,'name')]
td_pharmacy				xpath		//table[contains(@class,'table-striped')]/tbody/tr[not(contains(@class,'ng-hide'))]/td[contains(@ng-class,'name') and text()= '${pharmacyName}' ]
btn_addUser				id		pharmacy-btn-add-pharmacist
select_role                           css             select[ng-model='pharmacist.role']
btn_addPhar				id		pharmacist-btn-save

#EditPharmacy
btn_editPhar				id		pharmacy-btn-edit
p_editSuccessMsg			css		#zp-modal-success p
====================================================================================
