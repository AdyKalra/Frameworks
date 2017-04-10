#Choose Indication
================================================================================
div_indicationName			xpath		//div[@ng-click='selectDisease(disease)' and contains(.,'${indicationName}')]
patConsent_indicationName	        xpath		//select[@ng-change="selectCondition()" and contains(., '${indicationName}')]
div_practiceSpecialty			xpath		//div[@class='prong-disease-header']//p[contains(.,'${specialtyName}')]
a_indicationName			xpath		//div[@id='$']//a[contains(text(),'%')]
h4_rxSummaryHeader			css		.rx-summary-text
================================================================================
