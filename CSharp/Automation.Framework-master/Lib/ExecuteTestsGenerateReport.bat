SET NUnitConsolePath=%1
SET TestsDllPath=%2
SET TestCategory=%3
SET XmlResultsPath=%4
SET PicklesExepath=%5
SET FeatureFilesPath=%6
SET ReportOutputPath=%7


%NUnitConsolePath% %TestsDllPath% --where "cat == %TestCategory%" 

%PicklesExepath% --feature-directory=%FeatureFilesPath% --output-directory=%ReportOutputPath% --test-results-format=nunit3 --link-results-file=%XmlResultsPath% --documentation-format=dhtml

pause