#Choose Medication
================================================================================
span_medicationName			xpath		//span[@class='ng-binding' and text()='${medicationName}']
list_medication				xpath		//div[@id='datagrid']//li[@class='ng-scope']
link_skipStep				id		prescribe-interactions-step
patConsent_medication			xpath		//span[@class='ng-binding' and contains(., '${medicationName}')]
list_medType				css		#datagrid ul>li:not([ng-repeat])
h4_rxSummaryHeader			css		.rx-summary-text
================================================================================
