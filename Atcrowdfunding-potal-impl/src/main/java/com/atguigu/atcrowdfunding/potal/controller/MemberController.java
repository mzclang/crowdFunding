package com.atguigu.atcrowdfunding.potal.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.atguigu.atcrowdfunding.bean.Cert;
import com.atguigu.atcrowdfunding.bean.Member;
import com.atguigu.atcrowdfunding.bean.MemberCert;
import com.atguigu.atcrowdfunding.bean.Ticket;
import com.atguigu.atcrowdfunding.manager.service.CertService;
import com.atguigu.atcrowdfunding.potal.listenter.PassListener;
import com.atguigu.atcrowdfunding.potal.listenter.RefuseListener;
import com.atguigu.atcrowdfunding.potal.service.MemberService;
import com.atguigu.atcrowdfunding.potal.service.TicketService;
import com.atguigu.atcrowdfunding.util.AjaxResult;
import com.atguigu.atcrowdfunding.util.Const;
import com.atguigu.atcrowdfunding.vo.Data;

@Controller
@RequestMapping("/member")
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private CertService certService;
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private TaskService taskService;
	
	@RequestMapping("/uploadCert")
	public String uploadCert() {
		return "member/uploadCert";
	}
	
	@RequestMapping("/checkemail")
	public String checkemail() {
		return "member/checkemail";
	}
	
	@RequestMapping("/basicinfo")
	public String basicinfo() {
		return "member/basicinfo";
	}
	
	@RequestMapping("/checkauthcode")
	public String checkauthcode() {
		return "member/checkauthcode";
	}
	
	@RequestMapping("/apply")
	public String accttype(HttpSession session) {
		
		Member member = (Member) session.getAttribute(Const.LOGIN_MEMBER);
		
		Ticket ticket = ticketService.getTicketByMemberId(member.getId());
		
		if(ticket==null) {
			ticket = new Ticket(); //封装数据
			
			ticket.setMemberid(member.getId());
			ticket.setPstep("apply");
			ticket.setStatus("0");
			
			ticketService.saveTicket(ticket);
			
		}else {
			String pstep = ticket.getPstep();
			
			if("accttype".equals(pstep)) {
				
				return "redirect:/member/basicinfo.htm";
				
			}else if("basicinfo".equals(pstep)) {
				
				//根据当前用户查询账户类型，然后根据类型查找需要上传的资质
				
				List<Cert> queryCertByAccttype = certService.queryCertByAccttype(member.getAccttype());
				
				session.setAttribute("queryCertByAccttype", queryCertByAccttype);
				
				return "redirect:/member/uploadCert.htm";
				
			}else if("uploadcert".equals(pstep)) {
				return "redirect:/member/checkemail.htm";
			}else if("checkemail".equals(pstep)) {
				return "redirect:/member/checkauthcode.htm";
			}
			
		}
		
		
		return "member/accttype";
	}
	
	
	@ResponseBody
	@RequestMapping("/startProcess")
	public Object startProcess(HttpSession session,String email) {
		AjaxResult result = new AjaxResult();
		
		try {
			//获取登录会员信息
			Member loginMember = (Member) session.getAttribute(Const.LOGIN_MEMBER);
			
			//如果用户输入新的邮箱，将旧的的邮箱地址替换
			if(loginMember.getEmail().equals(email)) {
				loginMember.setEmail(email);
				memberService.updateEmail(loginMember);
			}
			
			//启动实名认证流程 - 系统自动发送邮箱，生成验证码，验证邮箱地址是否合法（模拟：银行卡）
			 ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey("auth").singleResult();
			
			 //toEmail
			 //loginacct
			 //authcode 验证码
			 //flag 审核实名认证时提供
			 //passListener
			 //refuseListener
			 StringBuilder authcode = new StringBuilder();
			 for (int i = 1; i <=4; i++) {
				 authcode.append(new Random().nextInt(10));
			}
			 
			 Map<String,Object> variables = new HashMap<String, Object>();
			 variables.put("toEmail",email);
			 variables.put("authcode",authcode);
			 variables.put("loginacct",loginMember.getLoginacct());
			 variables.put("passListener",new PassListener());
			 variables.put("refuseListener",new RefuseListener());
			 
			 
			// ProcessInstance startProcessInstanceByKey = runtimeService.startProcessInstanceByKey("auth");
			 ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId(),variables);
			
			//记录流程步聚：
			Ticket ticket = ticketService.getTicketByMemberId(loginMember.getId());
			ticket.setPstep("checkemail");
			ticket.setPiid(processInstance.getId());
			ticket.setAuthcode(authcode.toString());
			ticketService.updatePiidAndPstep(ticket);
			
			result.setSuccess(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		
		return result;
	}

	
	/**
	 * 更新账户类型
	 */
	@ResponseBody
	@RequestMapping("/updateAcctType")
	public Object updateAcctType(HttpSession session,String accttype) {
		AjaxResult result = new AjaxResult();
		
		try {
			//获取登录会员信息
			Member loginMember = (Member) session.getAttribute(Const.LOGIN_MEMBER);
			loginMember.setAccttype(accttype);
			//更新账户类型
			memberService.updateAcctType(loginMember);
			
			//记录流程步聚：
			Ticket ticket = ticketService.getTicketByMemberId(loginMember.getId());
			ticket.setPstep("accttype");
			ticketService.updatePstep(ticket);
			
			result.setSuccess(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		
		return result;
	}
	
	
	
	@ResponseBody
	@RequestMapping("/updateBasicinfo")
	public Object updateBasicinfo(HttpSession session,Member member) {
		AjaxResult result = new AjaxResult();
		
		try {
			//获取登录会员信息
			Member loginMember = (Member) session.getAttribute(Const.LOGIN_MEMBER);
			loginMember.setRealname(member.getRealname());
			loginMember.setCardnum(member.getCardnum());
			loginMember.setTel(member.getTel());
			
			//更新数据类型
			memberService.updateBasicinfo(loginMember);
			
			//记录流程步聚：
			Ticket ticket = ticketService.getTicketByMemberId(loginMember.getId());
			ticket.setPstep("basicinfo");
			ticketService.updatePstep(ticket);
			
			result.setSuccess(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/finishApply")
	public Object finishApply(HttpSession session,String authcode) {
		AjaxResult result = new AjaxResult();
		
		try {
			//获取登录会员信息
			Member loginMember = (Member) session.getAttribute(Const.LOGIN_MEMBER);
			
			//让当前系统用户完成：验证码审核任务
			
			Ticket ticket = ticketService.getTicketByMemberId(loginMember.getId());
			if(ticket.getAuthcode().equals(authcode)) {
				//完成审核验证码任务
				Task task = taskService.createTaskQuery().processInstanceId(ticket.getPiid()).taskAssignee(loginMember.getLoginacct()).singleResult();
				taskService.complete(task.getId());
				
				//更新用户申请状态
				loginMember.setAuthstatus("1");
				memberService.updateAuthstatus(loginMember);
				
				
				//记录流程步聚：
				ticket.setPstep("finishapply");
				ticketService.updatePstep(ticket);
				result.setSuccess(true);
			}else {
				result.setSuccess(false);
				result.setMessage("验证码不正确，请重新输入！");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping("/doUploadCert")
	public Object doUploadCert(HttpSession session,Data ds) {
		AjaxResult result = new AjaxResult();
		
		try {
			//获取登录会员信息
			Member loginMember = (Member) session.getAttribute(Const.LOGIN_MEMBER);
			
			
	
			String realPath = session.getServletContext().getRealPath("/pics");
			
			
			List<MemberCert> certimgs = ds.getCertimgs();
			for (MemberCert memberCert : certimgs) {
				//资质文件上传
				MultipartFile  fileImg = memberCert.getFileImg();
				
				String extName = fileImg.getOriginalFilename().substring(fileImg.getOriginalFilename().lastIndexOf("."));
				
				String tmpName = UUID.randomUUID().toString() + extName;
				
				String filername = realPath + "/cert"+"/" + tmpName ;
				
				fileImg.transferTo(new File(filername)); //资质文件上传
				
				//准备数据
				memberCert.setIconpath(tmpName); //封装数据，保存数据库、
				
				memberCert.setMemberid(loginMember.getId());
			}
			//保存会员与资质关系数据
			certService.saveMemberCert(certimgs);
			
			//记录流程步聚：
			Ticket ticket = ticketService.getTicketByMemberId(loginMember.getId());
			ticket.setPstep("uploadcert");
			ticketService.updatePstep(ticket);
			
			result.setSuccess(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		
		return result;
	}
	
}
