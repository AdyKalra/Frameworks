#Notification
================================================================================
td_notfctnMsg				xpath		(//td[@class='message']/span)[1]
i_notfctnToggle 			css		.tableFloatingHeaderOriginal .notification-toggle-wrapper i
a_toggleUnread			 	id		header-toggle-check-unread
btn_filterAction 			id 		filter-btn-action
a_markRead				id		filter-action-read

#Patient End
inp_slctAllPatient			css		#datagrid th>input

#Pharmacy
span_rxInfo				xpath		(//td[@class='rx_information']//span[contains(.,'${medicationName}')])[1]
span_notfctnCount			css		#doctor-notifications .zp-nav-badge-text
================================================================================
