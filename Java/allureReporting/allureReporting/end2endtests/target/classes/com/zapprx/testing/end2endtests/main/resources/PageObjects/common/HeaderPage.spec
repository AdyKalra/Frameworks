#HeaderPage
================================================================================
headerTxt				xpath		//div[contains(@class,'header')]//img/following-sibling::span
span_username				id		dropdownMenuDD
txt_breadcrumb				css		li[ng-if='breadcrumb.page']
hdr_breadcrumb				css		li[ng-if='breadcrumb.section']
link_pracProfile			id		practice_link
link_settings				xpath		//ul[@id='advancedSearchDD']//a/i[contains(text(),'Settings')]
span_hdrTxt				id		header-rx-process
hdrTxt_patConsent			css		.text-left
link_myProfile				id		doctor_link

#patient
span_drpdwnProfile			id		zp-navbar-dropdown-profile-btn
span_logOut				id		zp-navbar-dropdown-logout

#physician #pharmacy
link_logOut				css		a[ng-click='logout()']

#admin
hdrtxt_admin				xpath		//span[@class='text' and contains(.,'Admin')]
btn_addNewPrac				css		button[ng-click='addPractice()']
li_editHdr				css		.breadcrumb li
================================================================================
