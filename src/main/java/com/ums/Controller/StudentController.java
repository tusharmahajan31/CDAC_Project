package com.ums.Controller;


import java.security.Principal;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.ums.repository.AccountingRepo;
import com.ums.repository.BatchRecodeRepo;
import com.ums.repository.DateSheetHeaderRepo;
import com.ums.repository.DateSheetRepo;
import com.ums.repository.ExamListRepository;
import com.ums.repository.ExamOldListRepo;
import com.ums.repository.GPARecordRepo;
import com.ums.repository.PaperRepository;
import com.ums.repository.StudentFeeRecordRepo;
import com.ums.repository.UserRepository;
import com.ums.repository.StudentsRepository;
import com.ums.repository.SubjectRepo;
import com.ums.repository.TeacherRepo;
import com.ums.entity.ResultList;
import com.ums.entity.Students;
import com.ums.entity.Subject;
import com.ums.entity.User;
import com.ums.helper.Message;


@Controller
@RequestMapping("/Student")
public class StudentController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private StudentsRepository StudentsRepository;
	@Autowired
	StudentFeeRecordRepo studentFeeRecordRepo;
	@Autowired
	private PaperRepository paperRepository;
	@Autowired
	ExamListRepository examListRepository;
	@Autowired
	BatchRecodeRepo batchRecordRepo;
	@Autowired
	DateSheetRepo dateSheetRepo;
	@Autowired
	DateSheetHeaderRepo dateSheetHeaderRepo;
	@Autowired
	SubjectRepo subjectRepo;
	@Autowired
	ExamOldListRepo examOldListRepo;
	@Autowired
	GPARecordRepo gpaRecordRepo;
	@Autowired
	TeacherRepo teacherRepo;
	@Autowired
	AccountingRepo acccountingRepo;
	
	@ModelAttribute
	public void addCommonData(Model model, Principal principal,HttpSession session) {
		String userName = principal.getName();
		User user = userRepository.getUserByUserName(userName);
		if(user.getStatus().equals("Student")) {
			model.addAttribute(user);
		}else {
			session.setAttribute("message", new Message("Try agian...!!", "alert-danger"));
			
		}
		
	}
	@RequestMapping("/index")
	public String dashboard(Model model) {
		
		model.addAttribute("title","This is Dashboard Page");

		
		int totalResults =(int) this.paperRepository.count();
		int totalRepeatStudents=this.paperRepository.getAllRepeaterListStudents();
		int totalImproveStudents=this.paperRepository.getAllImproveListStudents();
		model.addAttribute("totalResults",totalResults);
		model.addAttribute("totalRepeateStudents",totalRepeatStudents);
		model.addAttribute("totalImproveStudents",totalImproveStudents);
		
		return "Student/dashboard";
	}
	

	@RequestMapping("/student-result-data")
	public String StudentBatchResult(Model model, @RequestParam(value = "page",defaultValue = "0" ) int page,
			RedirectAttributes redirectAttributes,
			Principal principal) {
		model.addAttribute("title", "Student Result Data Page");
		Pageable pageable = PageRequest.of(page, 10);
		Page<Students> studentBatch = this.StudentsRepository.getAllStudentsBatch(pageable);
		model.addAttribute("batchRecord", studentBatch);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", studentBatch.getTotalPages());

		return "examination/student-result-data";
	}

	
	@RequestMapping("/StuProfile")
	public String StudentProfile(@RequestParam(value = "id", required = false, defaultValue = "0") int id,@RequestParam(value = "page", required = false, defaultValue = "0") int page,Model model,
			Principal principal ) {
		
		model.addAttribute("title", "Profile");
		Pageable pageable = PageRequest.of(page, 10);
	Students StudentProfile = this.StudentsRepository.getStudentsById(id);
    Page<Students>allStudents  = this.StudentsRepository.getAllStudents(pageable);

	model.addAttribute("allStudents",allStudents);
	model.addAttribute("currentPage", page);
	model.addAttribute("totalPages", allStudents.getTotalPages());
		
		
      	return "Student/StuProfile";
	}
	
	@RequestMapping("/result")
	public String resultList(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			@RequestParam(value = "batch", required = false, defaultValue = "Bcs-17") String batch,
			@RequestParam(value = "semester", required = false, defaultValue = "1st") String semester, Model model,
			Principal principal, Subject subject) {
		model.addAttribute("title", "User Result List Page");
		Pageable pageable = PageRequest.of(page, 10);
		System.out.print(batch);
		Page<ResultList> resultList = this.paperRepository.getAllRsultListByPage(pageable, batch, semester);
		List<Subject> subjects = this.subjectRepo.getSubjectBySemester(semester);
		List<Students> allStudents=this.StudentsRepository.getAllStudentsByBatch(batch);
		model.addAttribute("allStudents",allStudents);
		model.addAttribute("subjects", subjects);
		model.addAttribute("resultList", resultList);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", resultList.getTotalPages());
		model.addAttribute("batch", batch);
		model.addAttribute("semester", semester);

		return "Student/result";
	}
	
	
	
	
	
}

