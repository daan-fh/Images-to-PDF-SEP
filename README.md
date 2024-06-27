# Report for Assignment 1

## Images to PDF

Name: Images-to-PDF-SEP

URL: https://github.com/daan-fh/Images-to-PDF-SEP/tree/master 

Number of lines of code and the tool used to count it: 15.1 KLOC and the tool used was Lizard

Programming language: Java

## Coverage measurement

### Existing tool
The tool that was used was the in-built coverage tool of Android Studio and it was used by right clicking on the test folder and then clicking on the run with coverage. 

<Inform the name of the existing tool that was executed and how it was executed>

<Show the coverage results provided by the existing tool with a screenshot>


### Your own coverage tool

<The following is supposed to be repeated for each group member>

<Arnav>

Function name: getFilePath
Link to commit that shows code to gather coverage measurements: https://github.com/Swati4star/Images-to-PDF/commit/9d4a6bd9d3a8924568abefaa5a2c22c0d4d8210b#diff-a0424ad2bc7b675473aebd2059cb6a48b2a7a6cb2bdf857b422386dace8169da  
<Provide a screenshot of the coverage results output by the instrumentation>



Function 2 name: stripExtension

Link to commit that shows code to gather coverage measurements:
https://github.com/Swati4star/Images-to-PDF/commit/9d4a6bd9d3a8924568abefaa5a2c22c0d4d8210b#diff-47b812a63fa7e356d9cbf59cc48b1c30372ff82ab63f8e48ac2f98c582b794e4 

<Provide the same kind of information provided for Function 1>



## Coverage improvement

### Individual tests

<Test 1>

	Link to a commit made to show new/enhanced test: https://github.com/Swati4star/Images-to-PDF/commit/9d4a6bd9d3a8924568abefaa5a2c22c0d4d8210b#diff-f888c18ae9cd385296c717fe1b26d75605402607ede2eb63f1e4275104af8ce4 

Screenshot of the old coverage results:






Screenshot of the new coverage results: 



The coverage improved by 100% as beforehand there were no tests made for this function.

<Test 2>
	Link to a commit made to show new/enhanced test: https://github.com/Swati4star/Images-to-PDF/commit/9d4a6bd9d3a8924568abefaa5a2c22c0d4d8210b#diff-f888c18ae9cd385296c717fe1b26d75605402607ede2eb63f1e4275104af8ce4 

	Screenshot of the old coverage results:
	

	Screenshot of the new coverage results:



The coverage improved by 100% as beforehand there were no tests made for this function.

<Daan>

<Function 1 name> getFileName

<Show a patch (diff) or a link to a commit made in your forked repository that shows the instrumented code to gather coverage measurements> https://github.com/daan-fh/Images-to-PDF-SEP/commit/d5f58574e48e465bb4a6609a7baf933ba453ffd4


<Provide a screenshot of the coverage results output by the instrumentation>


<Function 2 name> printFile

<Provide the same kind of information provided for Function 1> https://github.com/daan-fh/Images-to-PDF-SEP/commit/d5f58574e48e465bb4a6609a7baf933ba453ffd4



## Coverage improvement

### Individual tests

<Test 1>

<Show a patch (diff) or a link to a commit made in your forked repository that shows the new/enhanced test> https://github.com/daan-fh/Images-to-PDF-SEP/commit/d5f58574e48e465bb4a6609a7baf933ba453ffd4


<Provide a screenshot of the old coverage results (the same as you already showed above)>

<Provide a screenshot of the new coverage results>

<State the coverage improvement with a number and elaborate on why the coverage is improved>
Enhanced this test, to also check for the case that path == null
Therefore the coverage went from 50% to 100%.

<Test 2>

<Provide the same kind of information provided for Test 1>



Added these two tests to hit all the branches in the function which improved the coverage from 0% to 100%.

<Byera>

Function name: checkRepeat

Link to commit that shows code to gather coverage measurements: 
https://github.com/Swati4star/Images-to-PDF/commit/038ad141b6a61718fe88072c029db71f8ee91919




Function name: getUniqueFileName

Link to commit that shows code to gather coverage measurements: 
https://github.com/Swati4star/Images-to-PDF/commit/d348d7a2ec02a299e97169dbdb252e1da2e8f7ac




## Coverage improvement

### Individual tests

<Test 1>

https://github.com/Swati4star/Images-to-PDF/commit/038ad141b6a61718fe88072c029db71f8ee91919





The coverage of the specific function is 100%, which shows a complete coverage of the function.

<Test 2>

https://github.com/Swati4star/Images-to-PDF/commit/d348d7a2ec02a299e97169dbdb252e1da2e8f7ac





The coverage of the specific function is 100%, which shows a complete coverage of the function.

<Alec>

Function name: getStyleValueFromName

Link: https://github.com/daan-fh/Images-to-PDF-SEP/commit/cc1d678c5d58b370c3ac82fb03729b74ff3cfbff 




	Function name: getLastFileName

Link: https://github.com/daan-fh/Images-to-PDF-SEP/commit/216057f979a3b411ebfba8758e2ae3915c02b73a 



## Coverage improvement

### Individual tests


<Test 1>

Link: https://github.com/daan-fh/Images-to-PDF-SEP/commit/d57c017fee4316ea93b09b77e3c227693600504a 







As there was no test made for this function, the coverage improvement for this function is 100%.

<Test 2>

Link: https://github.com/daan-fh/Images-to-PDF-SEP/commit/da88757b8e066eef369ddf47ab39e4d684d4c573




As there was no test made for this function, the coverage improvement for this function is 100%.

### Overall

<Provide a screenshot of the old coverage results by running an existing tool (the same as you already showed above)>


<Provide a screenshot of the new coverage results by running the existing tool using all test modifications made by the group>


## Statement of individual contributions

<Write what each group member did>
Alec - Implemented the coverage for getLastFileName and getStyleValueFromName and made the tests for both of these functions.
Arnav - Acquired the number of lines in the project using Lizard and implemented tests for getFilePath in the FileUriUtil file and stripExtention in the FileUtils file.
Byera - Instrumented the functions checkRepeat and getUniqueFileName. Then made tests respectively which achieved complete branch coverage and hence improving the total coverage.
Daan - Forked the repo and implemented the coverage for getFileName and printFile. Enhanced and added tests to improve the branch coverage for the functions.

