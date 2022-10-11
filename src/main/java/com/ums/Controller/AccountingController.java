package com.ums.Controller;

import java.security.Principal;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sun.istack.Nullable;
import com.ums.repository.AccountingRepo;
import com.ums.repository.StudentFeeRecordRepo;
import com.ums.repository.UserRepository;
import com.ums.entity.User;


@Controller
@RequestMapping("/Accounting")
public class AccountingController {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private StudentFeeRecordRepo studentFeeRecordRepo;
	
	@Autowired
	private AccountingRepo accountingRepo;
	
	
	@ModelAttribute
	public void addCommonData(Model model, Principal principal) {
		String userName = principal.getName();
		User user = userRepository.getUserByUserName(userName);
		model.addAttribute(user);

	}
	
	@RequestMapping("/index")
	public String dashboard(Model model) {
		@Nullable
		int grossIncome=0;
		@Nullable
		int netIcome=0;
		int totalSemesterFee=0;
		int totalRegistrationFee=0;
		model.addAttribute("title", "This is Accounting Dashboard Page");
			if(this.studentFeeRecordRepo.getGrossIncome()!=null) {
			grossIncome=this.studentFeeRecordRepo.getGrossIncome();
			}
			if(this.studentFeeRecordRepo.getNetIncome()!=null) {
			netIcome=this.studentFeeRecordRepo.getNetIncome();
			}
		
			if(this.accountingRepo.getTotalSemesterFee()!=null) {
			totalSemesterFee=this.accountingRepo.getTotalSemesterFee();
			}
			if(this.accountingRepo.getTotalRegistrationFee()!=null) {
			totalRegistrationFee=this.accountingRepo.getTotalRegistrationFee();
			}		
		
		
		model.addAttribute("grossIncome",grossIncome+totalSemesterFee+totalRegistrationFee);
		model.addAttribute("netIcome",(netIcome+totalRegistrationFee+totalSemesterFee));
		 
		return "Accounting/home";
	}
	
	@RequestMapping("/balance-sheet")
	public String balanceSheet(Model model) {

		model.addAttribute("title", "This is Accounting Dashboard Page");
		
		int grossIncome=0;
		int totalRegistrationFee=0;
		int totalSemesterFee=0;
		
		
		
		if(this.studentFeeRecordRepo.getGrossIncome()!=null) {
		grossIncome=this.studentFeeRecordRepo.getGrossIncome();
		}
		if(this.accountingRepo.getTotalRegistrationFee()!=null) {
		totalRegistrationFee=this.accountingRepo.getTotalRegistrationFee();
		}
		if(this.accountingRepo.getTotalSemesterFee()!=null) {
		totalSemesterFee=this.accountingRepo.getTotalSemesterFee();
		}
	
		model.addAttribute("grossIncome",grossIncome);
	
		model.addAttribute("totalRegistrationFee",totalRegistrationFee);
		model.addAttribute("totalSemesterFee",totalSemesterFee);
		return "Accounting/balance-sheet";
	}
	
	

	@RequestMapping("/balance-sheet-search")
	public String balanceSheetSearch(Model model,@RequestParam("startDate")Date startDate,@RequestParam("endDate")Date endDate) {

		model.addAttribute("title", "This is Accounting Dashboard Page");
		
		int grossIncome=0;
		
		int totalRegistrationFee=0;
		int totalSemesterFee=0;
		
		
		if(this.studentFeeRecordRepo.getGrossIncomeByDate(startDate.toString(),endDate.toString())!=null) {
		grossIncome=this.studentFeeRecordRepo.getGrossIncomeByDate(startDate.toString(),endDate.toString());
		}
		
		if(this.accountingRepo.getTotalRegistrationFeeByDate(startDate, endDate)!=null) {
		totalRegistrationFee=this.accountingRepo.getTotalRegistrationFeeByDate(startDate, endDate);
		}
		if(this.accountingRepo.getTotalSemesterFeeByDate(startDate, endDate)!=null) {
		totalSemesterFee=this.accountingRepo.getTotalSemesterFeeByDate(startDate, endDate);
		}
	
		model.addAttribute("grossIncome",grossIncome);

		model.addAttribute("totalRegistrationFee",totalRegistrationFee);
		model.addAttribute("totalSemesterFee",totalSemesterFee);
		return "Accounting/balance-sheet";
	}
	

		
	public static void getCurrentTimeUsingDate() {
	    Date date = new Date(0);
	    String strDateFormat = "hh:mm:ss a";
	    DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
	    String formattedDate= dateFormat.format(date);
	    System.out.println("Current time of the day using Date - 12 hour format: " + formattedDate);
	}
	
	
}
