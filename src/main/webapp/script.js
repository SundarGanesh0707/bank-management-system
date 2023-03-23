var details = {};
// ----SignIn----
function signIn(){
    console.log("hello");
	var emp_id = document.getElementById("empId").value;
    var password = document.getElementById("password").value;

    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function(){

        if(xhr.readyState == 4){
            var json = JSON.parse(xhr.responseText);
            if(json.statusCode == 200){
                document.cookie = "sessionId="+json.sessionId;
                if(json.role == "ADMIN"){
                    window.location.href = "./admin.html";
                }
                else if(json.role == "CASHIER"){
                    window.location.href = "./cashier.html";
                }
                else if(json.role == "MANAGER"){
                    window.location.href = "./manager.html";
                }
                else{
                    window.location.href = "./loanOfficer.html";
                }
                
                alert(json.message);
            }
            else{
                alert(json.message);
            }
        }

    };
    xhr.open("POST", "./SignIn");
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send("emp_id="+emp_id+"&password="+password);

}

function insertName(){
    getUserDetails();
    if(details != ""){
        document.getElementById("userName").innerHTML = " "+details.name;
    }
    if(details.role == "ADMIN"){
        switchToBranch();
    }
    else if(details.role == "CASHIER"){
        switchToTransition();
    }
    else if(details.role == "MANAGER"){
        switchToCustomer();
    }
    else{
        // switchToLoan();
    }
}

Date.prototype.toDateInputValue = (function() {
    var local = new Date(this);
    local.setMinutes(this.getMinutes() - this.getTimezoneOffset());
    return local.toJSON().slice(0,10);
});

//----get cookie-----
function getCookie(cname) {
    let name = cname + "=";
    let ca = document.cookie.split(';');
    for(let i = 0; i < ca.length; i++) {
      let c = ca[i];
      while (c.charAt(0) == ' ') {
        c = c.substring(1);
      }
      if (c.indexOf(name) == 0) {
        return c.substring(name.length, c.length);
      }
    }
    return "";
  }

// ----on load fuction----
function checkCookie(){
    var sessionId = getCookie("sessionId");
    if(sessionId != ""){
        var xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function(){
            if(xhr.readyState == 4){
                var json = JSON.parse(xhr.responseText);
                if(json.statusCode == 200){
                    if(json.role == "ADMIN"){
                        window.location.href = "./admin.html";
                    }
                    else if(json.role == "CASHIER"){
                        window.location.href = "./cashier.html";
                    }
                    else if(json.role == "MANAGER"){
                        window.location.href = "./manager.html";
                    }
                    else{
                        window.location.href = "./loanOfficer.html";
                    }
                }
                else if(json.statusCode == 500){
                    alert(json.message);
                }
            }
        };
        xhr.open("POST", "./IsCookieAlive");
        xhr.send();
    }
}

//_______________________________________________________________________________________________________ 

function switchToBranch(){
    document.getElementById("mainLeftSide").innerHTML = '<div id = "dashboard"><span id = "dashHead" style = "color: #4CAF50;">B</span><div id = "dashText">Branch dashboard</div></div><div class="dashOption" onclick="switchToAddBranch()">Add Branch</div><div class="dashOption" onclick="switchToViewTransactions()"> View Transactions</div>';
    switchToAddBranch();
}

function switchToEmployee(){
    document.getElementById("mainLeftSide").innerHTML = '<div id = "dashboard"><span id = "dashHead" style = "color: #4CAF50;">E</span><div id = "dashText">Employee dashboard</div></div><div class="dashOption" onclick="switchToAddEmployee()">Add Employee</div><div class="dashOption" onclick="switchToRemoveEmployee()"> Remove Employee</div><div class="dashOption" onclick="switchToViewEmployees()">View Employees</div>';
    switchToAddEmployee();
}

function switchToCustomer(){
    document.getElementById("mainLeftSide").innerHTML = '<div id = "dashboard"><span id = "dashHead" style = "color: #4CAF50;">C</span><div id = "dashText">Customer dashboard</div></div><div class="dashOption" onclick="switchToAddCustomer()">Add Customer</div><div class="dashOption" onclick="switchToViewCustomers()"> View Customers</div>';
    switchToAddCustomer();
}

function switchToManagerCustomer(){
    document.getElementById("mainLeftSide").innerHTML = '<div id = "dashboard"><span id = "dashHead" style = "color: #4CAF50;">C</span><div id = "dashText">Customer dashboard</div></div><div class="dashOption" onclick="switchToAddCustomer()">Add Customer</div><div class="dashOption" onclick="switchToViewCustomers()"> View Customers</div><div class="dashOption" onclick = "switchTochangeCustomerProfile()">Change Details</div>';
    switchToAddCustomer();
}

function switchToAccount(){
    document.getElementById("mainLeftSide").innerHTML = '<div id = "dashboard"><span id = "dashHead" style = "color: #4CAF50;">A</span><div id = "dashText">Account dashboard</div></div><div class="dashOption" onclick="switchToAddAccount()">Add Account</div><div class="dashOption" onclick="switchToRemoveAccount()"> Remove Account</div><div class="dashOption" onclick="switchToViewAccounts()"> View Accounts</div>';
    switchToAddAccount();
}

function switchToCashierAccount(){
    document.getElementById("mainLeftSide").innerHTML = '<div id = "dashboard"><span id = "dashHead" style = "color: #4CAF50;">A</span><div id = "dashText">Account dashboard</div></div><div class="dashOption" onclick="switchToViewAccounts()"> View Accounts</div>';
    switchToViewAccounts();
}

function switchToManagerEmployee(){
    document.getElementById("mainLeftSide").innerHTML = '<div id = "dashboard"><span id = "dashHead" style = "color: #4CAF50;">E</span><div id = "dashText">Employee dashboard</div></div><div class="dashOption" onclick="switchToViewEmployees()">View Employees</div>';
    switchToViewEmployees();
}

function switchToProfile(){
    document.getElementById("mainLeftSide").innerHTML = '<div id = "dashboard"><span id = "dashHead" style = "color: #4CAF50;">P</span><div id = "dashText">Profile dashboard</div></div><div class="dashOption" onclick="switchToViewProfile()">Profile</div><div class="dashOption" onclick="switchToChangePassword()">Change Password</div>';
    switchToViewProfile();
}

function switchToTransition(){
    document.getElementById("mainLeftSide").innerHTML = '<div id = "dashboard"><span id = "dashHead" style = "color: #4CAF50;">T</span><div id = "dashText">Transaction dashboard</div></div><div class="dashOption" onclick="switchToTransact()">Transaction</div><div class="dashOption" onclick="switchToTransfer()">Transfer</div><div class="dashOption" onclick="switchToPayLoan()">Pay Loan</div>';
    switchToTransact();
}




function switchToPayLoan(){
    document.getElementById("mainRightSide").innerHTML = '<div id = "topic">PAY LOAN</div><div id = "content" style = "width: 80%;height: 60%;"><div id = "inputFilds" style = "height:100%;display: flex;flex-direction: column;justify-content: space-between;width:50%;margin-left: 10%;" ><label>Loan Id</label><input type = "number" id = "loanId"><br><label>Account No</label><input type = "number" id="accountNo"><br><label>Date</label><input type = "date" id = "todayDate" readonly><br><label>Amount</label><input type = "number" id="amount"><br><div id="addBox" style = "height: 10%;"><button id = "submit" onclick="payLoan()" >SUBMIT</button></div></div>';
    document.getElementById("todayDate").value = new Date().toDateInputValue();
}

function payLoan(){

    var loanId = document.getElementById("loanId").value;
    var accountNo = document.getElementById("accountNo").value;
    var amount = document.getElementById("amount").value;

    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function(){
        if(xhr.readyState == 4){
            var json = JSON.parse(xhr.responseText);
            let ele = document.createElement("div");
            ele.id = "message";
            ele.innerHTML = json.message;
            element.parentNode.parentNode.remove();
            document.getElementById("content").parentNode.insertBefore(ele, document.getElementById("content"));
            if(json.statusCode == 200){
                ele.style.backgroundColor = "#4CAF50";
            }
            else{
                ele.style.backgroundColor = "#b53737";
            }
            setTimeout(function(){
                document.getElementById("message").remove();
                document.getElementById("loanId").value = "";
                document.getElementById("accountNo").value = "";
                document.getElementById("amount").value = "";
            }, 5000);
        }
    }

    xhr.open("POST", "./home/PayLoan");
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send("loanId="+loanId+"&accountNo="+accountNo+"&amount="+amount);
}














function switchToReceipt(){
    document.getElementById("mainLeftSide").innerHTML = '<div id = "dashboard"><span id = "dashHead" style = "color: #4CAF50;">R</span><div id = "dashText">Receipt dashboard</div></div><div class="dashOption" onclick="switchToCustomerReceipts()">Customer Receipts</div><div class="dashOption" onclick="switchToAccountReceipts()">Account Receipts</div>';
    switchToCustomerReceipts();
}

function switchToRequest(){
    document.getElementById("mainLeftSide").innerHTML = '<div id = "dashboard"><span id = "dashHead" style = "color: #4CAF50;">R</span><div id = "dashText">Request dashboard</div></div><div onclick = "switchToViewLoan()" class = "dashOption">View Loan</div><div class="dashOption" onclick="switchToLoanApprove()">Loan Approve</div>';
    switchToViewLoan();
}


function switchToLoan(){
    document.getElementById("mainLeftSide").innerHTML = '<div id = "dashboard" style="width:80%;"><span id = "dashHead" style = "color: #4CAF50;">L</span><div id = "dashText">Loan dashboard</div></div><div class="dashOption" onclick="switchToApplyLoan()">Apply Loan</div><div class = "dashOption" onclick="switchToViewLoan()">View Loan</div>';
    switchToApplyLoan();
}



function approveLoan(element, path, loanId, status){
    console.log("hellllllllllllllllooooooooooo");
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function(){
        if(xhr.readyState == 4){
            var json = JSON.parse(xhr.responseText);
            let ele = document.createElement("div");
            ele.id = "message";
            ele.innerHTML = json.message;
            element.parentNode.remove();
            document.getElementById("content").parentNode.insertBefore(ele, document.getElementById("content"));
            if(json.statusCode == 200){
                ele.style.backgroundColor = "#4CAF50";
            }
            else{
                ele.style.backgroundColor = "#b53737";
            }
            setTimeout(function(){
                document.getElementById("message").remove();
            }, 5000);

        }
    };
    xhr.open("POST", "./home/ApproveLoan");
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send("loanId="+loanId+"&path="+path+"&status="+status);
}






function switchToLoanApprove(){
    document.getElementById("mainRightSide").innerHTML = '<div id = "topic">APPROVE LOAN</div><div id = "searchBox"><div style = "display: flex;justify-content: center;align-items: center;width:50%;height:100%"><label style = "font-size: 18px;margin-top:-3%;">Loan Id : </label><input type = "number" id = "loanId"></div><button id = "submit" onclick="searchLoan()">SEARCH</button></div>    <div id ="empTable"><div class = "empTableHead"><div class = "loanID">LOAN ID</div><div class = "customerID">CUS ID & NAME</div><div class = "employeeDOB">DATE</div><div class = "employeePhoneNo">AMOUNT</div><div class= "LoanType">LOAN TYPE</div><div class= "customerAadharNo">BALANCE AMOUNT</div><div class= "customerGender">STATUS</div><div class = "accept"><i class="fa-solid fa-check"></i></div><div class = "reject"><i class="fa-solid fa-xmark"></i></div></div><div id = "childs"></div></div>';
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function(){
        if(xhr.readyState == 4){
            var json = JSON.parse(xhr.responseText);
            if(json[0].statusCode == 200){

                let child = document.getElementById("childs");
                for(let i=1; i<json.length; i++){
                    let element = document.createElement("div");
                    element.className = "empTableLeg";
                    // element.setAttribute("onclick","displayPdf(\""+json[i].path+"\")");
                    element.innerHTML = '<div class = "loanID" onclick = "displayPdf(\''+json[i].path+'\')">'+json[i].loanId+'</div><div class = "customerID" onclick = "displayPdf(\''+json[i].path+'\')">'+json[i].customerId+' & '+json[i].customerName+'</div><div class = "employeeDOB" onclick = "displayPdf(\''+json[i].path+'\')">'+json[i].date+'</div><div class = "employeePhoneNo" onclick = "displayPdf(\''+json[i].path+'\')">'+json[i].amount+'</div><div class= "LoanType" onclick = "displayPdf(\''+json[i].path+'\')">'+json[i].loanType+'</div><div class= "customerAadharNo" onclick = "displayPdf(\''+json[i].path+'\')">'+json[i].balancePayment+'</div><div class= "customerGender" onclick = "displayPdf(\''+json[i].path+'\')">'+json[i].loanStatus+'</div><div class = "accept" onclick = "approveLoan(this, \''+json[i].path+'\', '+json[i].loanId+', \'ACCEPT\')"><button class="acceptButton" ><i class="fa-solid fa-check"></i></button></div><div class = "reject" onclick = "approveLoan(this, \''+json[i].path+'\', '+json[i].loanId+', \'REJECT\')"><button class="rejectButton" ><i class="fa-solid fa-xmark"></i></button></div>';
                    child.appendChild(element);
                }
            }
            else{
                alert(json[0].message);
            }
        }
    };
    xhr.open("POST", "./home/ViewRequestLoan");
    xhr.send();
}


function cutTheIfarme(){
    document.getElementById("imageBox").style.display = "none";
}

function displayPdf(path){
    document.getElementById("imageBox").style.display = "flex";
    let frame = document.getElementById("frame");
    frame.src = "./home/ReadFile?path="+path;
}




























function switchToViewLoan(){
    document.getElementById("mainRightSide").innerHTML = '<div id = "topic">VIEW LOAN</div><div id = "searchBox"><div style = "display: flex;justify-content: center;align-items: center;width:50%;height:100%"><label style = "font-size: 18px;margin-top:-3%;">Loan Id : </label><input type = "number" id = "loanId"></div><button id = "submit" onclick="searchLoan()">SEARCH</button></div>    <div id ="empTable"><div class = "empTableHead"><div class = "employeeId">LOAN ID</div><div class = "employeeName">CUS ID & NAME</div><div class = "employeeDOB">DATE</div><div class = "employeePhoneNo">AMOUNT</div><div class= "employeeAddress">LOAN TYPE</div><div class= "customerAadharNo">BALANCE AMOUNT</div><div class= "customerGender">STATUS</div></div><div id = "childs"> </div></div> ';
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function(){
        if(xhr.readyState == 4){
            var json = JSON.parse(xhr.responseText);
            if(json[0].statusCode == 200){

                let child = document.getElementById("childs");
                for(let i=1; i<json.length; i++){
                    let element = document.createElement("div");
                    element.className = "empTableLeg";
                    element.innerHTML = '<div class = "employeeId">'+json[i].loanId+'</div><div class = "employeeName">'+json[i].customerId+' & '+json[i].customerName+'</div><div class = "employeeDOB">'+json[i].date+'</div><div class = "employeePhoneNo">'+json[i].amount+'</div><div class= "employeeAddress">'+json[i].loanType+'</div><div class= "customerAadharNo">'+json[i].balancePayment+'</div><div class= "customerGender">'+json[i].loanStatus+'</div>';
                    child.appendChild(element);
                }
            }
            else{
                alert(json[0].message);
            }
        }
    };
    xhr.open("POST", "./home/ViewLoan");
    xhr.send();

}


function searchLoan(){
    var loanId = document.getElementById("loanId").value;
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function(){
        if(xhr.readyState == 4){
            var json = JSON.parse(xhr.responseText);
            if(json.statusCode == 200){
                let child = document.getElementById("childs");
                child.innerHTML = "";
                let element = document.createElement("div");
                element.className = "empTableLeg";
                element.innerHTML = '<div class = "employeeId">'+json.loanId+'</div><div class = "employeeName">'+json.customerId+' & '+json.customerName+'</div><div class = "employeeDOB">'+json.date+'</div><div class = "employeePhoneNo">'+json.amount+'</div><div class= "employeeAddress">'+json.loanType+'</div><div class= "customerAadharNo">'+json.balancePayment+'</div><div class= "customerGender">'+json.loanStatus+'</div>';
                child.appendChild(element);
                
            }
            else{
                alert(json.message);
            }
        }
    };
    xhr.open("POST", "./home/SearchLoan");
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send("loanId="+loanId);
}



















































































function switchToApplyLoan(){
    document.getElementById("mainRightSide").innerHTML = '<div id = "topic">Apply Loan</div><div id = "Pbox"><div class = "loanBox" style="background-color: #4CAF50;"><p>HOME LOAN</p><div class = "aboutLoan">A home loan is a type of secured loan that is used to finance the purchase or construction of a property. The loan is secured by the property itself and has a fixed interest rate and repayment period.</div><h3>interest Rate : 6.80%</h3><button class = "applyButton" onclick="switchApplyLoan(6.80, \'homeloan\')">APPLY LOAN</button></div><div class="loanBox"><p>PERSONAL LOAN</p><div class = "aboutLoan">A personal loan is an unsecured loan that provides funds to the borrower for various personal expenses. It is not backed by any collateral and has a fixed interest rate and repayment period.</div><h3>interest Rate : 10.50%</h3><button class = "applyButton" onclick="switchApplyLoan(10.50, \'personalloan\')">APPLY LOAN</button></div><div class="loanBox" style="background-color: #212121;"><p>GOLD LOAN</p><div class = "aboutLoan">A gold loan is a secured loan where the borrower pledges their gold as collateral to the lender in exchange for a loan amount. The loan amount is determined by the value of the gold.</div><h3>interest Rate : 7.35%</h3><button class = "applyButton" onclick="switchApplyLoan(7.35, \'goldloan\')">APPLY LOAN</button></div></div>';
}

function switchApplyLoan(interestRate, loanType){
    document.getElementById("mainRightSide").innerHTML = '<div id = "topic" style = "margin-top: 5%;">APPLY LOAN</div><div id = "content" style = "width: 80%;margin-top: 2%;"><div class="pBox"><label>Customer Id</label><input type = "number" id="customerId"><br><label>Date</label><input type = "date" id = "todayDate" readonly><br><label>Account No</label><input type = "number" id="accountNo"><br><label>Interest Rate</label><input type = "text" id = "interestRate" readonly><br></div><div class="pBox"><label>Loan Type</label><input type = "text" id = "loanType" readonly><br><label>No Of Years</label><select type = "number" id = "noOfYears"><option value = "3">3 YEARS</option><option value = "5">5 YEARS</option><option value = "5">7 YEARS</option></select><br><label>Amount</label><input type = "number" id = "amount"><br><label>Choose The Income Certificate</label><input type = "file" id = "incomeFile"><br></div></div><div style="margin-bottom: 10%;"><button id = "submit" onclick="applyLoan()">APPLY</button></div>';
    document.getElementById("todayDate").value = new Date().toDateInputValue();
    document.getElementById("interestRate").value = interestRate +"%";
    document.getElementById("loanType").value = loanType.toUpperCase();
}


function applyLoan(){

    var customerId = document.getElementById("customerId").value;
    var accountNo = document.getElementById("accountNo").value;
    var interestRate = document.getElementById("interestRate").value;
    var loanType = document.getElementById("loanType").value;
    var noOfYears = document.getElementById("noOfYears").value;
    var amount = document.getElementById("amount").value;
    var pdfFile = document.getElementById("incomeFile");
    var ext = pdfFile.value.substring(pdfFile.value.lastIndexOf('.') + 1);
    if(amount < 50000){
        alert("Amount Is To Low");
        return;
    }

    if(loanType == "GOLDLOAN"){
        if(amount > 2000000.0){
            alert("Amount is To Large");
            return;
        }
    }
    else if(loanType == "PERSONALLOAN"){
        if(amount > 3500000.0){
            alert("Amount is To Large");
            return;
        }
    }
    else if(loanType = "HOMELOAN"){
        if(amount > 6000000.0){
            alert("Amount is To Large");
            return;
        }
    }
    if(ext == "pdf" ){
        createFile(pdfFile);
        if(path != ""){
            var xhr = new XMLHttpRequest();
            xhr.onreadystatechange = function(){
                if(xhr.readyState == 4 && xhr.status == 200){
                    document.getElementById("submit").disabled = true;
                    console.log(xhr.responseText);
                    var json = JSON.parse(xhr.responseText);
                    let ele = document.createElement("div");
                    ele.id = "message";
                    ele.innerHTML = json.message;
                    document.getElementById("content").parentNode.insertBefore(ele, document.getElementById("content"));
                    if(json.statusCode == 200){
                        ele.style.backgroundColor = "#4CAF50";
                    }
                    else{
                        ele.style.backgroundColor = "#b53737";
                    }
                    setTimeout(function(){
                        document.getElementById("message").remove();
                        document.getElementById("submit").disabled = false;
                        document.getElementById("customerId").value = "";
                        document.getElementById("accountNo").value = "";
                        document.getElementById("amount").value = "";
                    }, 5000);
                }
            }
            xhr.open("POST", "./home/ApplyLoan", false);
            xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xhr.send("customerId="+customerId+"&accountNo="+accountNo+"&loanType="+loanType+"&interestRate="+interestRate.substring(0, interestRate.length-1)+"&path="+path+"&amount="+amount+"&noOfYears="+noOfYears);
        }
        else{
            alert();
        }
    } 
    else{
        alert("Plz Choose The pdf File");
    }


}

var path = "";
function createFile(file){

    let xhr = new XMLHttpRequest();       
        let formdata = new FormData();
            formdata.append('file',file.files[0]);
            xhr.onreadystatechange = ()=>{
                if(xhr.readyState == 4){
                    var json = JSON.parse(xhr.responseText);
                    if(json.statusCode == 200){
                        console.log(json.path);
                        path = json.path
                    }
                }
            }
    xhr.open('POST','./home/CreateFile', false);        
    xhr.send(formdata);

}

function switchToCustomerReceipts(){
    document.getElementById("mainRightSide").innerHTML = '<div id = "topic">CUSTOMER RECEIPTS</div><div id = "searchBox"><div id = "customerSearch"><div class="smallBoxes"><label style = "margin-top: -3%;">CustomerId : </label><input type = "number" id = "customerId" class = "inputt"></div><div class="smallBoxes"><label style = "margin-top: -3%;">From Date : </label><input type = "date" id = "fromDate" class = "inputt"></div><div class="smallBoxes"><label style = "margin-top: -3%;">To Date : </label><input type = "date" id = "toDate" class = "inputt"></div></div><button id = "submit" onclick="searchCustomerReceipt()">SEARCH</button>  </div><div id = "histroyTable"><div class = "parent"><div class="subChild">DATE</div><div class="subChild">FROM ACCOUNT NO</div><div class="subChild">TO ACCOUNT NO</div><div class="subChild">TRANSITION TYPE</div><div class="subChild">AMOUNT</div><div class="subChild">BALANCE</div></div><div id = "childs"></div></div>';
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function(){
        if(xhr.readyState == 4){
            var json = JSON.parse(xhr.responseText);
		    if(json[0].statusCode == 200){
                let histroyTable = document.getElementById("childs");
                for(let i=1; i<json.length; i++){
                    let parent = document.createElement("div");
                    parent.setAttribute("class", "subparent"); 
                    parent.innerHTML = '<div class="childd">'+json[i].date+'</div><div class="childd">'+json[i].fromAccountNo+'</div><div class="childd">'+json[i].toAccountNo+'</div><div class="childd">'+json[i].transferType+'</div><div class="childd">'+json[i].amount+'</div><div class="childd">'+json[i].balance+'</div>';
                    histroyTable.appendChild(parent);
                }
		    }
		    else if(json[0].statusCode == 204){
                alert(json[0].message);
		    }
            else{
                alert(json[0].message);
            }
        }
    };
    xhr.open("POST", "./home/GetAllBranchTransaction");
    xhr.send();     
}





function searchCustomerReceipt(){

    var customerId = document.getElementById("customerId").value;
    var fromDate = document.getElementById("fromDate").value;
    var toDate = document.getElementById("toDate").value;

    var search = "";

    if(customerId != "" && fromDate != "" && toDate != ""){
        search ="all";
    }
    else if(fromDate != "" && toDate != ""){
        search = "fromAndTo";
    }
    else if(customerId != ""){
        search = "customerId";
    }
    else{
        alert("Plz Fill The Fildes");
        return;
    }


    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function(){
        if(xhr.readyState == 4){
            var json = JSON.parse(xhr.responseText);
		    if(json[0].statusCode == 200){
                let histroyTable = document.getElementById("childs");
                histroyTable.innerHTML = "";
                for(let i=1; i<json.length; i++){
                    let parent = document.createElement("div");
                    parent.setAttribute("class", "subparent"); 
                    parent.innerHTML = '<div class="childd">'+json[i].date+'</div><div class="childd">'+json[i].fromAccountNo+'</div><div class="childd">'+json[i].toAccountNo+'</div><div class="childd">'+json[i].transferType+'</div><div class="childd">'+json[i].amount+'</div><div class="childd">'+json[i].balance+'</div>';
                    histroyTable.appendChild(parent);
                }
		    }
		    else if(json[0].statusCode == 204){
                alert(json[0].message);
		    }
            else{
                alert(json[0].message);
            }
        }
    };
    xhr.open("POST", "./home/SearchCustomerReceipt");
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send("customerId="+customerId+"&fromDate="+fromDate+"&toDate="+toDate+"&search="+search);     


}


function switchToAccountReceipts(){
    document.getElementById("mainRightSide").innerHTML = '<div id = "topic">ACCOUNT RECEIPTS</div><div id = "searchBox"><div id = "customerSearch"><div class="smallBoxes"><label style = "margin-top: -3%;">AccountNo : </label><input type = "number" id = "accountNo" class = "inputt"></div><div class="smallBoxes"><label style = "margin-top: -3%;">From Date : </label><input type = "date" id = "fromDate" class = "inputt"></div><div class="smallBoxes"><label style = "margin-top: -3%;">To Date : </label><input type = "date" id = "toDate" class = "inputt"></div></div><button id = "submit" onclick="searchAccountReceipt()">SEARCH</button>  </div><div id = "histroyTable"><div class = "parent"><div class="subChild">DATE</div><div class="subChild">FROM ACCOUNT NO</div><div class="subChild">TO ACCOUNT NO</div><div class="subChild">TRANSITION TYPE</div><div class="subChild">AMOUNT</div><div class="subChild">BALANCE</div></div><div id = "childs"></div></div>';
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function(){
        if(xhr.readyState == 4){
            var json = JSON.parse(xhr.responseText);
		    if(json[0].statusCode == 200){
                let histroyTable = document.getElementById("childs");
                for(let i=1; i<json.length; i++){
                    let parent = document.createElement("div");
                    parent.setAttribute("class", "subparent"); 
                    parent.innerHTML = '<div class="childd">'+json[i].date+'</div><div class="childd">'+json[i].fromAccountNo+'</div><div class="childd">'+json[i].toAccountNo+'</div><div class="childd">'+json[i].transferType+'</div><div class="childd">'+json[i].amount+'</div><div class="childd">'+json[i].balance+'</div>';
                    histroyTable.appendChild(parent);
                }
		    }
		    else if(json[0].statusCode == 204){
                alert(json[0].message);
		    }
            else{
                alert(json[0].message);
            }
        }
    };
    xhr.open("POST", "./home/GetAllBranchTransaction");
    xhr.send();     
}


function searchAccountReceipt(){

    var accountNo = document.getElementById("accountNo").value;
    var fromDate = document.getElementById("fromDate").value;
    var toDate = document.getElementById("toDate").value;

    var search = "";

    if(accountNo != "" && fromDate != "" && toDate != ""){
        search ="all";
    }
    else if(fromDate != "" && toDate != ""){
        search = "fromAndTo";
    }
    else if(accountNo != ""){
        search = "accountNo";
    }
    else{
        alert("Plz Fill The Fildes");
        return;
    }


    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function(){
        if(xhr.readyState == 4){
            var json = JSON.parse(xhr.responseText);
		    if(json[0].statusCode == 200){
                let histroyTable = document.getElementById("childs");
                histroyTable.innerHTML = "";
                for(let i=1; i<json.length; i++){
                    let parent = document.createElement("div");
                    parent.setAttribute("class", "subparent"); 
                    parent.innerHTML = '<div class="childd">'+json[i].date+'</div><div class="childd">'+json[i].fromAccountNo+'</div><div class="childd">'+json[i].toAccountNo+'</div><div class="childd">'+json[i].transferType+'</div><div class="childd">'+json[i].amount+'</div><div class="childd">'+json[i].balance+'</div>';
                    histroyTable.appendChild(parent);
                }
		    }
		    else if(json[0].statusCode == 204){
                alert(json[0].message);
		    }
            else{
                alert(json[0].message);
            }
        }
    };
    xhr.open("POST", "./home/SearchAccountReceipt");
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send("accountNo="+accountNo+"&fromDate="+fromDate+"&toDate="+toDate+"&search="+search); 

}

function switchTochangeCustomerProfile(){
    document.getElementById("mainRightSide").innerHTML = '<div id = "topic">CHANGE DETAILS</div><div id = "content"><div id = "inputFildes"><label>Customer Id</label><input type = "number" id = "customerId"><br><label>New PhoneNo</label><input type = "number" id = "phoneNo"><br><label>New Address</label><input type = "text" id = "address"><br><button id = "submit" onclick="changeCustomerProfile()">CHANGE</button></div></div></div>';
}

function changeCustomerProfile(){

    var customerId = document.getElementById("customerId").value;
    var phoneNo = document.getElementById("phoneNo").value;
    var address = document.getElementById("address").value;
    var change = "";

    if(phoneNo != "" && address != ""){
        change = "both";
    }
    else if(phoneNo != ""){
        if(!(/^(?:(?:\+|0{0,2})91(\s*[\-]\s*)?|[0]?)?[789]\d{9}$/g.test(phoneNo))){
            document.getElementById("phoneNo").value = details.phoneNo;
            alert("Invaild Phone");
            return;
        }
        else{
            change = "phoneNo";
        }
    }
    else{
        if(address.length<10){
            document.getElementById("address").value = details.address;
            alert("Invaild Address");
            return;
        }
        else{
            change = "address";
        }
    }
    
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function(){
        if(xhr.readyState == 4){
            document.getElementById("submit").disabled = true;
            var json = JSON.parse(xhr.responseText);
            let ele = document.createElement("div");
            ele.id = "message";
            ele.innerHTML = json.message;
            document.getElementById("content").parentNode.insertBefore(ele, document.getElementById("content"));
            if(json.statusCode == 200){
                ele.style.backgroundColor = "#4CAF50";
            }
            else{
                ele.style.backgroundColor = "#b53737";
            }
            setTimeout(function(){
                document.getElementById("message").remove();
                document.getElementById("submit").disabled = false;
                document.getElementById("customerId").value = "";
                document.getElementById("phoneNo").value = "";
                document.getElementById("address").value = "";
            }, 5000);

        }
    };
    xhr.open("POST", "./home/ChangeCustomerProfile");
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send("customerId="+customerId+"&phoneNo="+phoneNo+"&address="+address+"&change="+change);
    
}

function switchToTransact(){
    document.getElementById("mainRightSide").innerHTML = '<div id = "topic">TRANSACT</div><div id = "content" style = "width: 80%;height: 80%;"><div id = "inputFilds" style = "height:100%;display: flex;flex-direction: column;justify-content: space-between;width:50%;margin-left: 10%;" ><label>Customer Id</label><input type = "number" id = "customerId"><br><label>Account No</label><input type = "number" id="accountNo"><br><label>Amount</label><input type = "number" id="amount"><br><label>Date</label><input type = "text" id = "todayDate" readonly><br><label>Transact Type</label><select id="transactType"><option value = "DEPOSITED">DEPOSIT</option><option value = "WITHDRAWN">WITHDRAW</option></select><br><label>Payment Type</label><select id="paymentType"><option value="CASH">CASH</option><option value="DEMAND_DRAFT">DEMAND_DRAFT</option><option value="CHEQUE">CHEQUE</option></select><br><div id="addBox" style = "height: 10%;"><button id = "submit" onclick="transact()" >SUBMIT</button></div></div>';
    document.getElementById("todayDate").value = new Date().toDateInputValue();
}

function switchToTransfer(){
    document.getElementById("mainRightSide").innerHTML = '<div id = "topic">TRANSFER</div><div id = "content" style = "width: 80%;height: 80%;"><div id = "inputFilds" style = "height:100%;display: flex;flex-direction: column;justify-content: space-between;width:50%;margin-left: 10%;" ><label>Customer Id</label><input type = "number" id = "customerId"><br><label>From Account No</label><input type = "number" id="fromAccountNo"><br><label>To Account No</label><input type = "number" id = "toAccountNo"><br><label>Amount</label><input type = "number" id="amount"><br><label>Date</label><input type = "text" id = "todayDate" readonly><br><label>Transact Type</label><select id="transferType"><option value = "TRANSFERED">TRANSFER</option></select><br><div id="addBox" style = "height: 10%;"><button id = "submit" onclick="transfer()" >SUBMIT</button></div></div>';
    document.getElementById("todayDate").value = new Date().toDateInputValue();
}


function transfer(){
    var customerId = document.getElementById("customerId").value;
    var date = document.getElementById("todayDate").value;
    var fromAccountNo = document.getElementById("fromAccountNo").value;
    var toAccountNo = document.getElementById("toAccountNo").value;
    var amount = document.getElementById("amount").value;
    var type = document.getElementById("transferType").value;
    if(customerId == "" || customerId == null){
        alert("Plz fill the all fildes");
        return;
    }
    if(amount > 200000){
        alert("Amount is to larrge");
        return;
    }
    if(amount < 100){
        alert("Amount is to Low");
        return;
    }
    if(fromAccountNo == toAccountNo){
        alert("Both accountNo are Same Plz Check");
        return;
    }
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function(){
        
		if(xhr.status == 200 && xhr.readyState == 4){
            document.getElementById("submit").disabled = true;
            console.log(xhr.responseText);
            var json = JSON.parse(xhr.responseText);
            let ele = document.createElement("div");
            ele.id = "message";
            ele.innerHTML = json.message;
            document.getElementById("content").parentNode.insertBefore(ele, document.getElementById("content"));
            if(json.statusCode == 200){
                ele.style.backgroundColor = "#4CAF50";
            }
            else{
                ele.style.backgroundColor = "#b53737";
            }
            setTimeout(function(){
                document.getElementById("message").remove();
                document.getElementById("submit").disabled = false;
                document.getElementById("customerId").value = "";
                document.getElementById("fromAccountNo").value = "";
                document.getElementById("amount").value = "";
                document.getElementById("toAccountNo").value = "";
            }, 5000);
		}
	}
    xhr.open("POST", "./home/Transfer", false);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send("customerId="+customerId+"&date="+date+"&fromAccountNo="+fromAccountNo+"&toAccountNo="+toAccountNo+"&amount="+amount+"&type="+type);


}


function transact(){
    var customerId = document.getElementById("customerId").value;
    var date = document.getElementById("todayDate").value;
    var accountNo = document.getElementById("accountNo").value;
    var amount = document.getElementById("amount").value;
    var type = document.getElementById("transactType").value;
    var payType = document.getElementById("paymentType").value;
    console.log("hello");
    if(amount > 200000){
        alert("Amount is to larrge");
        return;
    }
    if(amount < 100){
        alert("Amount is to Low");
        return;
    }
    
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function(){
        
		if(xhr.status == 200 && xhr.readyState == 4){
            document.getElementById("submit").disabled = true;
            var json = JSON.parse(xhr.responseText);
            let ele = document.createElement("div");
            ele.id = "message";
            ele.innerHTML = json.message;
            document.getElementById("content").parentNode.insertBefore(ele, document.getElementById("content"));
            if(json.statusCode == 200){
                ele.style.backgroundColor = "#4CAF50";
            }
            else{
                ele.style.backgroundColor = "#b53737";
            }
            setTimeout(function(){
                document.getElementById("message").remove();
                document.getElementById("submit").disabled = false;
                document.getElementById("customerId").value = "";
                document.getElementById("accountNo").value = "";
                document.getElementById("amount").value = "";
            }, 5000);
		}
	}
    xhr.open("POST", "./home/Transact", false);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send("customerId="+customerId+"&date="+date+"&accountNo="+accountNo+"&amount="+amount+"&type="+type+"&paymentType="+payType);
}


function searchBranchTransaction(){

    var IFSCCode = document.getElementById("branch").value;

    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function(){
        if(xhr.readyState == 4){
            var json = JSON.parse(xhr.responseText);
		    if(json[0].statusCode == 200){
                let histroyTable = document.getElementById("childs");
                histroyTable.innerHTML = '';
                for(let i=1; i<json.length; i++){
                    let parent = document.createElement("div");
                    parent.setAttribute("class", "subparent"); 
                    parent.innerHTML = '<div class="childd">'+json[i].date+'</div><div class="childd">'+json[i].fromAccountNo+'</div><div class="childd">'+json[i].toAccountNo+'</div><div class="childd">'+json[i].transferType+'</div><div class="childd">'+json[i].amount+'</div><div class="childd">'+json[i].balance+'</div>';
                    histroyTable.appendChild(parent);
                }
		    }
		    else if(json[0].statusCode == 204){
                alert(json[0].message);
		    }
            else{
                alert(json[0].message);
            }
        }
    };
    xhr.open("POST", "./home/SearchBranchTransaction");
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send("IFSCCode="+IFSCCode);


}

function searchAccount(){
    var accountNo = document.getElementById("accountNo").value;
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function(){
        if(xhr.readyState == 4){
            var json = JSON.parse(xhr.responseText);
		    if(json.statusCode == 200){
                let histroyTable = document.getElementById("childs");
                histroyTable.innerHTML = "";
                let parent = document.createElement("div");
                parent.setAttribute("class", "subparent"); 
                parent.innerHTML = '<div class="subChilds">'+json.accountNo+'</div><div class="subChilds">'+json.IFSCCode+'</div><div class="subChilds">'+json.balance+'</div>';
                histroyTable.appendChild(parent); 
		    }
		    else if(json.statusCode == 204){
                alert(json.message);
		    }
            else{
                alert(json.message);
            }
        }
    };
    xhr.open("POST", "./home/SearchAccountDetails");
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send("accountNo="+accountNo+"&role="+details.role);
}

function saveProfile(){

    var phoneNo = document.getElementById("phoneNo").value;
    var address = document.getElementById("address").value;

    if(details.phoneNo == phoneNo && address == details.address){
        alert("No Changes");
        return;
    }
    else{
        if(!(/^(?:(?:\+|0{0,2})91(\s*[\-]\s*)?|[0]?)?[789]\d{9}$/g.test(phoneNo))){
            document.getElementById("phoneNo").value = details.phoneNo;
            alert("Invaild Phone");
            return;
        }
        if(address.length<10){
            document.getElementById("address").value = details.address;
            alert("Invaild Address");
            return;
        }
        var xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function(){
            if(xhr.readyState == 4){
                document.getElementById("submit").disabled = true;
                var json = JSON.parse(xhr.responseText);
                let ele = document.createElement("div");
                ele.id = "message";
                ele.innerHTML = json.message;
                document.getElementById("content").parentNode.insertBefore(ele, document.getElementById("content"));
                if(json.statusCode == 200){
                    ele.style.backgroundColor = "#4CAF50";
                }
                else{
                    ele.style.backgroundColor = "#b53737";
                }
                setTimeout(function(){
                    document.getElementById("message").remove();
                    document.getElementById("submit").disabled = false;
                    getUserDetails();
                    document.getElementById("empId").value = details.empId;
                    document.getElementById("empName").value = details.name;
                    document.getElementById("phoneNo").value = details.phoneNo;
                    document.getElementById("address").value = details.address;
                    document.getElementById("dob").value = details.dob;
                    document.getElementById("role").value = details.role;
                }, 5000);
            }
        };
        xhr.open("POST", "./home/SaveProfile");
        xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xhr.send("phoneNo="+phoneNo+"&address="+address);
    }

}

function changePassword(){

    var isAlive = getCookie("passwordChanged");
    if(isAlive == "" || isAlive == null){
    var oldPassword = document.getElementById("oldPassword").value;
    var newPassword = document.getElementById("newPassword").value;
    var rePassword = document.getElementById("rePassword").value;
    var minMaxLength = /^[\s\S]{8,32}$/,upper = /[A-Z]/,lower = /[a-z]/,number = /[0-9]/,special = /[ !"#$%&'()*+,\-./:;<=>?@[\\\]^_`{|}~]/;
    if(oldPassword == newPassword){
        alert("Plz check Your new Password");
        return;
    }
    if(newPassword != rePassword){
        alert("Plz check confirm Password");
        return;
    }
    if(!(minMaxLength.test(newPassword) && upper.test(newPassword) && lower.test(newPassword) && number.test(newPassword) && special.test(newPassword))){
		alert("Plz create Strong Password");
		return;
	}
    else{
        var xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function(){
        
		    if(xhr.status == 200 && xhr.readyState == 4){
                document.getElementById("submit").disabled = true;
                var json = JSON.parse(xhr.responseText);
                let ele = document.createElement("div");
                ele.id = "message";
                ele.innerHTML = json.message;
                document.getElementById("content").parentNode.insertBefore(ele, document.getElementById("content"));
                if(json.statusCode == 200){
                    ele.style.backgroundColor = "#4CAF50";
                    setCookie("passwordChanged", true);
                }
                else{
                   ele.style.backgroundColor = "#b53737";
               }
                setTimeout(function(){
                    document.getElementById("message").remove();
                    document.getElementById("submit").disabled = false;
                    document.getElementById("oldPassword").value="";
                    document.getElementById("newPassword").value="";
                    document.getElementById("rePassword").value="";
                }, 5000);
		    }
	    }
        xhr.open("POST", "./home/ChangePassword", false);
        xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xhr.send("oldPassword="+oldPassword+"&newPassword="+newPassword);
    }
    }
    else{
        alert("Sorry, Today You already change your password ")
    }

}

//______________________________________________________________________________________________________

// Profile

function switchToViewProfile(){
    document.getElementById("mainRightSide").innerHTML = '<div id = "topic">PROFILE</div><div id = "content" style = "width: 80%;height: 80%;"><div id = "inputFilds" style = "height:100%;display: flex;flex-direction: column;justify-content: space-between;width:50%;margin-left: 10%;" ><label>Employee Id</label><input type = "text" id = "empId" readonly><br><label>Employee Name</label><input type = "text" id="empName" readonly><br><label>Phone No</label><input type = "number" id="phoneNo"><br><label>Address</label><input type = "text" id = "address"><br><label>Date Of Birth</label><input type = "text" id = "dob" readonly><br><label>Role</label><input type = "text" id = "role" readonly><br><div id="addBox" style = "height: 10%;"><button id = "submit" onclick="saveProfile()" >SAVE</button></div></div>';
    getUserDetails();
    document.getElementById("empId").value = details.empId;
    document.getElementById("empName").value = details.name;
    document.getElementById("phoneNo").value = details.phoneNo;
    document.getElementById("address").value = details.address;
    document.getElementById("dob").value = details.dob;
    document.getElementById("role").value = details.role;
}


function switchToChangePassword(){
    document.getElementById("mainRightSide").innerHTML = '<div id = "topic">CHANGE PASSWORD</div><div id = "content"><div id = "inputFildes"><label>Old Password</label><input type = "text" id = "oldPassword"><br><label>New Password</label><input type = "password" id = "newPassword"><br><label>Re-Password</label><input type = "password" id = "rePassword"><br><button id = "submit" onclick="changePassword()">CHANGE</button></div></div>';
}



// Branch

function switchToAddBranch(){
    document.getElementById("mainRightSide").innerHTML = '<div id = "topic">ADD BRANCH</div><div id = "content"><div id = "inputFildes"><label>Branch Name</label><input type = "text" id = "branchName"><br><label>Branch IFSCCode</label><input type = "text" id= "IFSCCode"><br><button id = "submit" onclick="addBranch()">ADD</button></div></div>';
}

function switchToViewTransactions(){
    document.getElementById("mainRightSide").innerHTML = '<div id = "topic">TRANSACTIONS</div><div id = "searchBox"><div style = "display: flex;justify-content: center;align-items: center;width:50%;height:100%"><label style = "font-size: 18px;margin-top:-3%;">Branch : </label><select id="branch"></select></div><button id = "submit" onclick="searchBranchTransaction()">SEARCH</button>  </div><div id = "histroyTable"><div class = "parent"><div class="subChild">DATE</div><div class="subChild">FROM ACCOUNT NO</div><div class="subChild">TO ACCOUNT NO</div><div class="subChild">TRANSITION TYPE</div><div class="subChild">AMOUNT</div><div class="subChild">BALANCE</div></div><div id = "childs"></div></div>';
    getBranchDetails();
    insertValue();

    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function(){
        if(xhr.readyState == 4){
            var json = JSON.parse(xhr.responseText);
		    if(json[0].statusCode == 200){
                let histroyTable = document.getElementById("childs");
                for(let i=1; i<json.length; i++){
                    let parent = document.createElement("div");
                    parent.setAttribute("class", "subparent"); 
                    parent.innerHTML = '<div class="childd">'+json[i].date+'</div><div class="childd">'+json[i].fromAccountNo+'</div><div class="childd">'+json[i].toAccountNo+'</div><div class="childd">'+json[i].transferType+'</div><div class="childd">'+json[i].amount+'</div><div class="childd">'+json[i].balance+'</div>';
                    histroyTable.appendChild(parent);
                }
		    }
		    else if(json[0].statusCode == 204){
                alert(json[0].message);
		    }
            else{
                alert(json[0].message);
            }
        }
    };
    xhr.open("POST", "./home/GetAllBranchTransaction");
    xhr.send();     
}



// Employee

function switchToViewEmployees(){
    document.getElementById("mainRightSide").innerHTML = '<div id = "topic">VIEW EMPLOYEE</div><div id = "searchBox"><div style = "display: flex;justify-content: center;align-items: center;width:50%;height:100%"><label style = "font-size: 18px;margin-top:-3%;">Employee Id : </label><input type = "number" id = "empId"></div><button id = "submit" onclick="searchEmployee()">SEARCH</button></div>        <div id ="empTable"><div class = "empTableHead"><div class = "employeeId">ID</div><div class = "employeeName">NAME</div><div class = "employeeDOB">DOB</div><div class = "employeePhoneNo">PHONE NO</div><div class= "employeeAddress">ADDRESS</div><div class= "employeeBranchId">BRANCH ID</div><div class= "employeeRole">ROLE</div></div><div id = "childs">    </div></div>';
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function(){
        if(xhr.readyState == 4){
            var json = JSON.parse(xhr.responseText);
            if(json[0].statusCode == 200){

                let child = document.getElementById("childs");

                for(let i=1; i<json.length; i++){
                    let element = document.createElement("div");
                    element.className = "empTableLeg";
                    element.innerHTML = '<div class = "employeeId">'+json[i].empId+'</div><div class = "employeeName">'+json[i].empName+'</div><div class = "employeeDOB">'+json[i].dob+'</div><div class = "employeePhoneNo">'+json[i].phoneNo+'</div><div class= "employeeAddress">'+json[i].address+'</div><div class= "employeeBranchId">'+json[i].branchId+'</div><div class= "employeeRole">'+json[i].role+'</div>';
                    child.appendChild(element);
                }


                // let parentDiv = document.getElementById("parent");
                // for(let i = 1; i<json.length; i++){
                //     let ele = document.createElement("div");
                //     ele.className = "child";
                //     ele.innerHTML += '<div>Id : '+json[i].empId+'</div><br><div>Name : '+json[i].empName+'</div><br><div>DOB : '+json[i].dob+'</div><br><div>PhoneNo : '+json[i].phoneNo+'</div><br><div>Address : '+json[i].address+'</div><br><div>Branch Id : '+json[i].branchId+'</div><br><div>Role : '+json[i].role+'</div>';
                //     parentDiv.appendChild(ele); 
                //     parentDiv.innerHTML += "<br><br>";
                // }

            }
            else{
                alert(json[0].message);
            }
        }
    };
    xhr.open("POST", "./home/ViewEmployee");
    xhr.send();

}

function switchToRemoveEmployee(){
    document.getElementById("mainRightSide").innerHTML = '<div id = "topic">REMOVE EMPLOYEE</div><div id = "content"><div id = "inputFildes"><label>Employee Id</label><input type = "number" id = "empId"><br><label>Resson</label><textarea id = "resson"></textarea><br><button id = "submit" onclick="removeEmployee()">REMOVE</button></div></div>';
}

function removeEmployee(){

    var empId = document.getElementById("empId").value;
    var resson = document.getElementById("resson").value;

    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function(){
        if(xhr.readyState == 4){
            document.getElementById("submit").disabled = true;
            var json = JSON.parse(xhr.responseText);
            let ele = document.createElement("div");
            ele.id = "message";
            ele.innerHTML = json.message;
            document.getElementById("content").parentNode.insertBefore(ele, document.getElementById("content"));
            if(json.statusCode == 200){
                ele.style.backgroundColor = "#4CAF50";
            }
            else{
                ele.style.backgroundColor = "#b53737";
            }
            setTimeout(function(){
                document.getElementById("message").remove();
                document.getElementById("submit").disabled = false;
                document.getElementById("empId").value = "";
                document.getElementById("resson").value = "";
            }, 5000);

        }
    };
    xhr.open("POST", "./home/RemoveEmployee");
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send("empId="+empId+"&resson="+resson);

}

function switchToAddEmployee(){
    document.getElementById("mainRightSide").innerHTML = '<div id = "topic">ADD EMPLOYEE</div><div id ="addOption" ><div id = "wholeOp"><div id = "excited" class="opAdd" onclick="switchExcitedEmployee()">EXISTED</div><div id = "new" class = "opAdd" onclick="switchToNewEmployee()">NEW</div></div></div><div id = "content"></div>';
    switchExcitedEmployee();
}

function switchExcitedEmployee(){
    document.getElementById("excited").style.backgroundColor = "#4CAF50";
    document.getElementById("new").style.background = "none";
    document.getElementById("content").style.width = "100%";
    document.getElementById("content").innerHTML = '<div id = "inputFildes"><label>Employee Id</label><input type = "number" id = "empId"><br><label>Choose The Branch</label><select id = "branch"></select><br><button id = "submit" onclick="addExcitedEmployee()">ADD</button></div>';
    getBranchDetails();
    insertValue();
}

function switchToNewEmployee(){
    document.getElementById("new").style.backgroundColor = "#4CAF50";
    document.getElementById("excited").style.background = "none";
    document.getElementById("content").style.width = "80%";
    document.getElementById("content").innerHTML = '<div class="pBox"><label>Employee Name</label><input type = "text" id="empName"><br><label>Choose The Branch</label><select id = "branch"></select><br><label>Phone No</label><input type = "number" id="phoneNo"><br><label>Address</label><input type = "text" id = "address"><br></div><div class="pBox"><label>Date Of Birth</label><input type = "date" id = "dob"><br><label>ROLE</label><select id = "role"><option value="CASHIER">CASHIER</option><option value="LOAN_OFFICER">LOAN_OFFICER</option><option value="MANAGER">MANAGER</option></select><br><label>Password</label><input type = "password" id = "password"><br><div id="addBox"><button id = "submit" onclick="addNewEmployee()" >ADD</button></div></div>';
    getBranchDetails();
    insertValue();
}



// Customer

function switchToAddCustomer(){
    document.getElementById("mainRightSide").innerHTML = '<div id = "topic">ADD CUSTOMER</div><div id = "content" style = "width: 80%;"><div class="pBox"><label>Customer Name</label><input type = "text" id="customerName"><br><label>Choose The Branch</label><select id = "branch"></select><br><label>Phone No</label><input type = "number" id="phoneNo"><br><label>Address</label><input type = "text" id = "address"><br></div><div class="pBox"><label>Date Of Birth</label><input type = "date" id = "dob"><br><label>Aadhar No</label><input type = "number" id = "aadharNo"><br><label>Choose The Gender</label><select id = "gender"><option value = "MALE">MALE</option><option value = "FEMALE">FEMALE</option><option value = "OTHER">OTHER</option></select><br><div id="addBox"><button id = "submit" onclick="addNewCustomer()" >ADD</button></div></div></div>';
    getBranchDetails();
    insertValue();
}

function switchToViewCustomers(){
    document.getElementById("mainRightSide").innerHTML = '<div id = "topic">VIEW CUSTOMER</div><div id = "searchBox"><div style = "display: flex;justify-content: center;align-items: center;width:50%;height:100%"><label style = "font-size: 18px;margin-top:-3%;">Customer Id : </label><input type = "number" id = "customerId"></div><button id = "submit" onclick="searchCustomer()">SEARCH</button></div>    <div id ="empTable"><div class = "empTableHead"><div class = "employeeId">ID</div><div class = "employeeName">NAME</div><div class = "employeeDOB">DOB</div><div class = "employeePhoneNo">PHONE NO</div><div class= "employeeAddress">ADDRESS</div><div class= "customerAadharNo">Aadhar No</div><div class= "customerGender">Gender</div></div><div id = "childs"> </div></div>';
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function(){
        if(xhr.readyState == 4){
            var json = JSON.parse(xhr.responseText);
            if(json[0].statusCode == 200){

                let child = document.getElementById("childs");
                for(let i=1; i<json.length; i++){
                    let element = document.createElement("div");
                    element.className = "empTableLeg";
                    element.innerHTML = '<div class = "employeeId">'+json[i].customerId+'</div><div class = "employeeName">'+json[i].customerName+'</div><div class = "employeeDOB">'+json[i].dob+'</div><div class = "employeePhoneNo">'+json[i].phoneNo+'</div><div class= "employeeAddress">'+json[i].address+'</div><div class= "customerAadharNo">'+json[i].aadharNo+'</div><div class= "customerGender">'+json[i].gender+'</div>';
                    child.appendChild(element);
                }


                // let parentDiv = document.getElementById("parent");
                // for(let i = 1; i<json.length; i++){
                //     let ele = document.createElement("div");
                //     ele.className = "child";
                //     ele.innerHTML += '<div>Id : '+json[i].customerId+'</div><br><div>Name : '+json[i].customerName+'</div><br><div>DOB : '+json[i].dob+'</div><br><div>PhoneNo : '+json[i].phoneNo+'</div><br><div>Address : '+json[i].address+'</div><br><div>Aadhar No : '+json[i].aadharNo+'</div><br><div>Gender : '+json[i].gender+'</div>';
                //     parentDiv.appendChild(ele); 
                //     parentDiv.innerHTML += "<br><br>";
                // }

            }
            else{
                alert(json[0].message);
            }
        }
    };
    xhr.open("POST", "./home/ViewCustomer");
    xhr.send();
}



// Account 

function switchToAddAccount(){
    document.getElementById("mainRightSide").innerHTML = '<div id = "topic">ADD ACCOUNT</div><div id = "content"><div id = "inputFildes"><label>Customer Id</label><input type = "number" id = "customerId"><br><label>Branch</label><select id = "branch"></select><br><label>Starting Amount</label><input type = "number" id = "amount"><br><button id = "submit" onclick="addAccount()">ADD</button></div></div>';
    getBranchDetails();
    insertValue();
}


function switchToRemoveAccount(){
    document.getElementById("mainRightSide").innerHTML = '<div id = "topic">REMOVE ACCOUNT</div><div id = "content"><div id = "inputFildes"><label>Account No</label><input type = "number" id = "accountNo"><br><label>Resson</label><textarea id = "resson"></textarea><br><button id = "submit" onclick="removeAccount()">ADD</button></div></div>';
}


function switchToViewAccounts(){
    document.getElementById("mainRightSide").innerHTML = '<div id = "topic">ACCOUNT DETAILS</div><div id = "searchBox"><div style = "display: flex;justify-content: center;align-items: center;width:50%;height:100%"><label style = "font-size: 18px;margin-top:-3%;">Account No : </label><input type = "number" id = "accountNo"></div><button id = "submit" onclick="searchAccount()">SEARCH</button>  </div><div id = "histroyTable"><div class = "parent"><div class="subChilds">ACCOUNT NO</div><div class="subChilds">BRANCH ID</div><div class="subChilds">BALANCE</div></div><div id = "childs"></div></div>';
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function(){
        if(xhr.readyState == 4){
            var json = JSON.parse(xhr.responseText);
		    if(json[0].statusCode == 200){
                let histroyTable = document.getElementById("childs");
                for(let i=1; i<json.length; i++){
                    let parent = document.createElement("div");
                    parent.setAttribute("class", "subparent"); 
                    parent.innerHTML = '<div class="subChilds">'+json[i].accountNo+'</div><div class="subChilds">'+json[i].IFSCCode+'</div><div class="subChilds">'+json[i].balance+'</div>';
                    histroyTable.appendChild(parent);
                }
		    }
		    else if(json[0].statusCode == 204){
                alert(json[0].message);
		    }
            else{
                alert(json[0].message);
            }
        }
    };
    xhr.open("POST", "./home/GetAccountDetails");
    xhr.send();   
}


function switchToViewEmployeeAccounts(){
    document.getElementById("mainRightSide").innerHTML = '<div id = "topic">ACCOUNT DETAILS</div><div id = "searchBox"><div style = "display: flex;justify-content: center;align-items: center;width:50%;height:100%"><label style = "font-size: 18px;margin-top:-3%;">Account No : </label><input type = "number" id = "accountNo"></div><button id = "submit" onclick="searchAccount()">SEARCH</button>  </div><div id = "histroyTable"><div class = "parent"><div class="subChilds">ACCOUNT NO</div><div class="subChilds">BRANCH ID</div><div class="subChilds">BALANCE</div></div><div id = "childs"></div></div>';
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function(){
        if(xhr.readyState == 4){
            var json = JSON.parse(xhr.responseText);
		    if(json[0].statusCode == 200){
                let histroyTable = document.getElementById("childs");
                for(let i=1; i<json.length; i++){
                    let parent = document.createElement("div");
                    parent.setAttribute("class", "subparent"); 
                    parent.innerHTML = '<div class="subChilds">'+json[i].accountNo+'</div><div class="subChilds">'+json[i].IFSCCode+'</div><div class="subChilds">'+json[i].balance+'</div>';
                    histroyTable.appendChild(parent);
                }
		    }
		    else if(json[0].statusCode == 204){
                alert(json[0].message);
		    }
            else{
                alert(json[0].message);
            }
        }
    };
    xhr.open("POST", "./home/GetEmployeesAccountDetails");
    xhr.send();
}


//______________________________________________________________________________________________________

function removeAccount(){

    var accountNo = document.getElementById("accountNo").value;
    var resson = document.getElementById("resson").value;

    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function(){
        if(xhr.readyState == 4){
            document.getElementById("submit").disabled = true;
            var json = JSON.parse(xhr.responseText);
            let ele = document.createElement("div");
            ele.id = "message";
            ele.innerHTML = json.message;
            document.getElementById("content").parentNode.insertBefore(ele, document.getElementById("content"));
            if(json.statusCode == 200){
                ele.style.backgroundColor = "#4CAF50";
            }
            else{
                ele.style.backgroundColor = "#b53737";
            }
            setTimeout(function(){
                document.getElementById("message").remove();
                document.getElementById("submit").disabled = false;
                document.getElementById("accountNo").value = "";
                document.getElementById("resson").value = "";
            }, 5000);

        }
    };
    xhr.open("POST", "./home/RemoveAccount");
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send("accountNo="+accountNo+"&resson="+resson);

}


function addAccount(){

    var customerId = document.getElementById("customerId").value;
    var IFSCCode = document.getElementById("branch").value;
    var amount = document.getElementById("amount").value;
    
    if(amount < 500){
        alert("Amount is To low");
        return;
    }
    else if(amount > 2000000){
        alert("Amount is To Large");
        return;
    }

    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function(){
        if(xhr.readyState == 4){
            document.getElementById("submit").disabled = true;
            var json = JSON.parse(xhr.responseText);
            let ele = document.createElement("div");
            ele.id = "message";
            ele.innerHTML = json.message;
            document.getElementById("content").parentNode.insertBefore(ele, document.getElementById("content"));
            if(json.statusCode == 200){
                ele.style.backgroundColor = "#4CAF50";
            }
            else{
                ele.style.backgroundColor = "#b53737";
            }
            setTimeout(function(){
                document.getElementById("message").remove();
                document.getElementById("submit").disabled = false;
                document.getElementById("customerId").value = "";
                document.getElementById("amount").value = "";
            }, 5000);

        }
    };
    xhr.open("POST", "./home/AddAccount");
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send("customerId="+customerId+"&amount="+amount+"&IFSCCode="+IFSCCode);

}


function getUserDetails(){
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function(){
        if(xhr.readyState == 4){
            var json = JSON.parse(xhr.responseText);
            if(json.statusCode == 200){
                details.empId = json.empId
                details.name = json.name;
                details.dob = json.dob;
                details.address = json.address;
                details.phoneNo = json.phoneNo;
                details.branchId = json.branchId;
                details.role = json.role;
                return details;
            }
            else{
                alert(json.message);
                return "";
            }
        }
    };
    xhr.open("POST", "./home/GetUserDetails", false);
    xhr.send();
}

function addBranch(){
    var branchName = document.getElementById("branchName").value;
    var IFSCCode = document.getElementById("IFSCCode").value;

    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function(){
        if(xhr.readyState == 4){
            document.getElementById("submit").disabled = true;
            var json = JSON.parse(xhr.responseText);
            let ele = document.createElement("div");
            ele.id = "message";
            ele.innerHTML = json.message;
            document.getElementById("content").parentNode.insertBefore(ele, document.getElementById("content"));
            if(json.statusCode == 200){
                ele.style.backgroundColor = "#4CAF50";
            }
            else{
                ele.style.backgroundColor = "#b53737";
            }
            setTimeout(function(){
                document.getElementById("message").remove();
                document.getElementById("submit").disabled = false;
                document.getElementById("branchName").value = "";
                document.getElementById("IFSCCode").value = "";
            }, 5000);

        }
    };
    xhr.open("POST", "./home/AddBranch");
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send("branchName="+branchName+"&IFSCCode="+IFSCCode);
    
}

function addExcitedEmployee(){

    var empId = document.getElementById("empId").value;
    var IFSCCode = document.getElementById("branch").value;

    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function(){
        if(xhr.readyState == 4){
            document.getElementById("submit").disabled = true;
            var json = JSON.parse(xhr.responseText);
            let ele = document.createElement("div");
            ele.id = "message";
            ele.innerHTML = json.message;
            document.getElementById("content").parentNode.insertBefore(ele, document.getElementById("content"));
            if(json.statusCode == 200){ 
                ele.style.backgroundColor = "#4CAF50";
            }
            else{
                ele.style.backgroundColor = "#b53737";
            }
            setTimeout(function(){
                document.getElementById("message").remove();
                document.getElementById("submit").disabled = false;
                document.getElementById("empId").value = "";
            }, 5000);
        }
    };

    xhr.open("POST", "./home/AddExcitedEmployee");
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send("emp_id="+empId+"&IFSCCode="+IFSCCode);

}

function addNewEmployee(){

    var empName = document.getElementById("empName").value;
    var IFSCCode = document.getElementById("branch").value;
    var phoneNo = document.getElementById("phoneNo").value;
    var address = document.getElementById("address").value;
    var dob = new Date(document.getElementById("dob").value);
    var role = document.getElementById("role").value;
    var password = document.getElementById("password").value;

    var diff_ms = Date.now() - dob.getTime();
    var age_dt = new Date(diff_ms); 
    let age = Math.abs(age_dt.getUTCFullYear() - 1970);
    if(age < 18){
		alert("Sorry your not eligible");
		document.getElementById("empName").value = "";
		document.getElementById("dob").value = "";
		document.getElementById("address").value = "";
		document.getElementById("phoneNo").value = "";
		document.getElementById("password").value = "";
		return;
	}

    var minMaxLength = /^[\s\S]{8,32}$/,upper = /[A-Z]/,lower = /[a-z]/,number = /[0-9]/,special = /[ !"#$%&'()*+,\-./:;<=>?@[\\\]^_`{|}~]/;
    
    // if(!(/^[a-zA-Z][a-zA-Z\\s]+$/g.test(empName))){
    //     alert("Invaild Name");
    //     return;
    // }

    if(!(/^(?:(?:\+|0{0,2})91(\s*[\-]\s*)?|[0]?)?[789]\d{9}$/g.test(phoneNo))){
		alert("Invaild Phone");
		return;
	}
	if(address.length<10){
		alert("Invaild Address");
		return;
	}
	
	if(!(minMaxLength.test(password) && upper.test(password) && lower.test(password) && number.test(password) && special.test(password))){
		alert("Plz create Strong Password");
		return;
	}

    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function(){
        if(xhr.readyState == 4){
            document.getElementById("submit").disabled = true;
            var result = xhr.responseText;
            var json = JSON.parse(result.substring(0, result.length-2));
            let ele = document.createElement("div");
            ele.id = "message";
            ele.innerHTML = json.message;
            document.getElementById("content").parentNode.insertBefore(ele, document.getElementById("content"));
            if(json.statusCode == 200){ 
                ele.style.backgroundColor = "#4CAF50";
            }
            else{
                ele.style.backgroundColor = "#b53737";
            }
            setTimeout(function(){
                document.getElementById("message").remove();
                document.getElementById("submit").disabled = false;
                document.getElementById("empName").value = "";
		        document.getElementById("dob").value = "";
		        document.getElementById("address").value = "";
		        document.getElementById("phoneNo").value = "";
		        document.getElementById("password").value = "";
            }, 5000);

        }
    };
    xhr.open("POST", "./home/AddNewEmployee");
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send("empName="+empName+"&phoneNo="+phoneNo+"&address="+address+"&dob="+document.getElementById("dob").value+"&role="+role+"&IFSCCode="+IFSCCode+"&password="+password);

}

let key = [];
let value = [];
function getBranchDetails(){
    key = [];
    value = [];
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function(){
		if(xhr.status == 200 && xhr.readyState == 4){
			var json = JSON.parse(xhr.responseText);
			for(let i=0; i<json.length; i++){
				key.push(json[i].key);
				value.push(json[i].value);
			}
		}
	}
	xhr.open("POST", "./GetBranchDetails", false);
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.send();
}

function insertValue(){
	let element = document.getElementById("branch");
	for(let i=0; i<key.length; i++){
		let ele = document.createElement("option");
		ele.value = value[i];
		ele.innerText = key[i];
		element.appendChild(ele);
	}
}

                
function logout(){
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function(){
		if(xhr.status == 200 && xhr.readyState == 4){
			var json = JSON.parse(xhr.responseText);
			if(json.statusCode == 200){
                alert(json.message);
                document.cookie = "sessionId=";
                window.history.back();
            }
            else if(json.statusCode == 400){
                alert(json.message);
                document.cookie = "sessionId=";
                window.history.back();
            }
            else{

            }
		}
	}
	xhr.open("POST", "./home/Logout");
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.send();
    
}

function searchEmployee(){

    var empId = document.getElementById("empId").value;

    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function(){
        if(xhr.readyState == 4){
            var json = JSON.parse(xhr.responseText);
            if(json.statusCode == 200){
                let child = document.getElementById("childs");
                child.innerHTML = "";
                let element = document.createElement("div");
                element.className = "empTableLeg";
                element.innerHTML = '<div class = "employeeId">'+json.empId+'</div><div class = "employeeName">'+json.empName+'</div><div class = "employeeDOB">'+json.dob+'</div><div class = "employeePhoneNo">'+json.phoneNo+'</div><div class= "employeeAddress">'+json.address+'</div><div class= "employeeBranchId">'+json.branchId+'</div><div class= "employeeRole">'+json.role+'</div>';
                child.appendChild(element);
            }
            else{
                alert(json.message);
                switchToViewEmployees();
            }
        }
    };
    xhr.open("POST", "./home/SearchEmployee");
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send("empId="+empId);

}


function addNewCustomer(){

    var customerName = document.getElementById("customerName").value;
    var IFSCCode = document.getElementById("branch").value;
    var dob = new Date(document.getElementById("dob").value);
    var phoneNo = document.getElementById("phoneNo").value;
    var address = document.getElementById("address").value;
    var aadharNo = document.getElementById("aadharNo").value;
    var gender = document.getElementById("gender").value;

    var diff_ms = Date.now() - dob.getTime();
    var age_dt = new Date(diff_ms); 
    let age = Math.abs(age_dt.getUTCFullYear() - 1970);
    if(age < 18){
		alert("Sorry, He is not eligible");
		document.getElementById("customerName").value = "";
		document.getElementById("dob").value = "";
		document.getElementById("address").value = "";
		document.getElementById("phoneNo").value = "";
		document.getElementById("aadharNo").value = "";
		return;
	}

    if(!(/^(?:(?:\+|0{0,2})91(\s*[\-]\s*)?|[0]?)?[789]\d{9}$/g.test(phoneNo))){
		alert("Invaild Phone");
		return;
	}
	if(address.length<10){
		alert("Invaild Address");
		return;
	}

    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function(){
        if(xhr.readyState == 4){
            document.getElementById("submit").disabled = true;
            var json = JSON.parse(xhr.responseText);
            let ele = document.createElement("div");
            ele.id = "message";
            ele.innerHTML = json.message;
            document.getElementById("content").parentNode.insertBefore(ele, document.getElementById("content"));
            if(json.statusCode == 200){ 
                ele.style.backgroundColor = "#4CAF50";
                alert("New CustomerId : "+json.customerId+" And AccountNo : "+json.accountNo);
            }
            else{
                ele.style.backgroundColor = "#b53737";
            }
            setTimeout(function(){
                document.getElementById("message").remove();
                document.getElementById("submit").disabled = false;
                document.getElementById("customerName").value = "";
		        document.getElementById("dob").value = "";
		        document.getElementById("address").value = "";
		        document.getElementById("phoneNo").value = "";
		        document.getElementById("role").value = "";
            }, 5000);

        }
    };
    xhr.open("POST", "./home/AddCustomer");
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send("customerName="+customerName+"&phoneNo="+phoneNo+"&address="+address+"&dob="+document.getElementById("dob").value+"&aadharNo="+aadharNo+"&IFSCCode="+IFSCCode+"&gender="+gender);

}


function searchCustomer(){

    var customerId = document.getElementById("customerId").value;

    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function(){
        if(xhr.readyState == 4){
            var json = JSON.parse(xhr.responseText);
            if(json.statusCode == 200){
                let child = document.getElementById("childs");
                child.innerHTML = "";
                let element = document.createElement("div");
                element.className = "empTableLeg";
                element.innerHTML = '<div class = "employeeId">'+json.customerId+'</div><div class = "employeeName">'+json.customerName+'</div><div class = "employeeDOB">'+json.dob+'</div><div class = "employeePhoneNo">'+json.phoneNo+'</div><div class= "employeeAddress">'+json.address+'</div><div class= "customerAadharNo">'+json.aadharNo+'</div><div class= "customerGender">'+json.gender+'</div>';
                child.appendChild(element);
            }
            else{
                alert(json.message);
                switchToViewEmployees();
            }
        }
    };
    xhr.open("POST", "./home/SearchCustomer");
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send("customerId="+customerId);

}

var setCookie = function (name, value) {
    var date = new Date(),
        expires = 'expires=';
    date.setTime(date.getTime() + 31536000000);
    expires += date.toGMTString();
    document.cookie = name + '=' + value + '; ' + expires + '; path=/';
}

        // var xhr = new XMLHttpRequest();
        // xhr.onreadystatechange = function(){
        //     if(xhr.readyState == 4){
        //         var json = JSON.parse(xhr.responseText);
        //         if(json.statusCode == 200){

        //         }
        //         else{
        //             alert(json.message);
        //         }
        //     }
        // };
        // xhr.open("POST", "./");
        // xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        // xhr.send("emp_id="+emp_id+"&password="+password);