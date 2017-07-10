:://///////////////////////////////////////////////////////////////////////////
:: This batch file is responsible for running SpecFlow tests and generating
:: a specflow test run report.
::
:: Usage:
::	RunTests.bat [-tags [tag1,tag2]]
::		-tags: 			Optionally specify which tags to run
:://///////////////////////////////////////////////////////////////////////////
@echo off
@setlocal

call :Defaults
call :ParseArguments %*
call :RunTests
::call :GenerateSpecFlowReport
call :GenerateNunitReport


:Defaults
rem ===========================================================================
SET NUNIT_EXE=%~dp0packages\NUnit.ConsoleRunner.3.6.1\tools\nunit3-console.exe
SET SPECFLOW_EXE=%~dp0packages\SpecFlow.2.1.0\Tools\specflow.exe
SET REPORTUNIT_EXE=%~dp0packages\ReportUnit.1.2.1\tools\ReportUnit.exe
SET TEST_RESULTS=%~dp0TestResults.xml
SET TEST_OUTPUT=%~dp0TestOutput.txt
SET TEST_FILE=%~dp0Zukini.nunit
SET TEST_PROJ=%~dp0Zukini.UI.Examples.Features\Zukini.UI.Examples.Features.csproj
SET TEST_RESULTS_HTML=%~dp0TestResults.html

goto :eof

:ParseArguments
rem ===========================================================================
if /I .%1 == . goto :eof
if /I .%1 == .-tags goto :ArgumentTags

:ArgumentTags
rem ===========================================================================
SET TAGS=%2
shift
shift
goto :ParseArguments


:RunTests
rem ===========================================================================
@echo ********************* Running Tests *********************************
IF DEFINED TAGS (
	%NUNIT_EXE% /labels:ON /out:%TEST_OUTPUT% /result:%TEST_RESULTS%;format=nunit3 /where:"cat == %TAGS%" %TEST_FILE%
) ELSE (
	%NUNIT_EXE% /labels:ON /out:%TEST_OUTPUT% /result:%TEST_RESULTS%;format=nunit3 %TEST_FILE%
)

if ERRORLEVEL 1 goto :Error
goto :eof

::GenerateSpecFlowReport
:GenerateNunitReport
rem ===========================================================================
@echo **************** Generating Nunit Report *************************
%REPORTUNIT_EXE%  %TEST_RESULTS% 

if ERRORLEVEL 1 goto :Error
goto :eof


:Exit
rem ===========================================================================
exit /b 0

:Error
rem ===========================================================================
exit /b 1

:End
