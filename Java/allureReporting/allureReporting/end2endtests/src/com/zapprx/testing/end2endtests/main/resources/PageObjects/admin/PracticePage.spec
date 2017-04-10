#NewPracticePage
====================================================================================
hdr_newPrac				css		div[title='New Practice'] h2
inp_name				id		name
inp_street				id		street1
inp_city				id		city
select_state				id		state
inp_zip					id		zip
inp_email				id		admin-email
inp_firstName    			id		admin-firstname
inp_lastName				id		admin-lastname
inp_password				id		password
inp_cnfrmPasswrd			id		confirm-password
btn_savePracProfile			id		practice-profile-edit-save
p_successMsg				css		#zp-modal-success p
btn_continue				id		zp-modal-success-btn
td_pracName				xpath		//table[contains(@class,'table-striped')]/tbody/tr[not(contains(@class,'ng-hide'))]/td[contains(@ng-class,'name')]
inp_npiNo				css		input[ng-model='practice.npi_number']

#EditPractice
p_updatedMsg				css		#zp-modal-success p
btn_editPractice                      id             practice-btn-edit
select_workflowMode                   css            select[ng-model="practice.pa_workflow"]
btn_back				css		button[ng-click="viewPractice()"]

#Add new doctor
inp_firstname				id		first_name
inp_lastname				id		last_name
inp_licNo				id		license_number
inp_deaNo				id		dea_number
inp_npiNoDoc				id		npi_number
inp_emailDoc				id		email
inp_degree				id		degree
inp_specialty				id		specialty
inp_cnfrmPasswdDoc		        id		confirmpassword
td_practice                             xpath           //table[contains(@class,'table-striped')]/tbody/tr[not(contains(@class,'ng-hide'))]/td[contains(@ng-class,'name') and text()= '${pName}' ]
select_role                             css             select[ng-model='doctor.role']
btn_updateProfileAddUser              id             doctor-btn-save
p_userSuccessMsg      			css		#zp-modal-success p
inp_isAdmin				id		is_admin
inp_showAll				id		show_all_patients
inp_canSubmit				id		can_submit
select_presNotificationPrefs		css		.notification-preferences select[ng-model="doctor.notify_by"]
select_paNotificationPrefs		css		.notification-preferences select[ng-model="doctor.pa_notify_by"]

#PracticeProfile
btn_addUser                           id             practice-btn-add-doctor
td_userList				css		td[ng-class*='actor.last_name']
li_nursesTab				id		practice-tab-nurses
li_physicianTab				id		practice-tab-physicians
txt_userName				xpath		//div[@class='datagrid']//td[contains(text(),'${firstName}')]	
====================================================================================
