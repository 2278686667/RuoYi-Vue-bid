package com.ruoyi.bid.controller;

import com.ruoyi.bid.domain.BidList;
import com.ruoyi.bid.domain.EvaluationExpertsInformation;
import com.ruoyi.bid.domain.FileInfomation;
import com.ruoyi.bid.domain.FolderStructure;
import com.ruoyi.bid.enums.FolderStructureState;
import com.ruoyi.bid.service.IBidListService;
import com.ruoyi.bid.service.IEvaluationExpertsInformationService;
import com.ruoyi.bid.service.IFileInfomationService;
import com.ruoyi.bid.service.IFolderStructureService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 招投标Controller
 *
 * @author chb
 * @date 2022-11-27
 */
@RestController
@RequestMapping("/bid/invite_tenders")
public class BidListController extends BaseController {
    @Autowired
    private IBidListService bidListService;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private IEvaluationExpertsInformationService iEvaluationExpertsInformationService;

    @Autowired
    private IFolderStructureService folderStructureService;

    @Autowired
    private IFileInfomationService fileInfomationService;


    /**
     * 查询招投标列表
     */
//    @PreAuthorize("@ss.hasPermi('bid:invite_tenders:list')")
    @GetMapping("/list")
    public TableDataInfo list(BidList bidList) {
        startPage();
        List<BidList> list = bidListService.selectBidListList(bidList);
        return getDataTable(list);
    }

    /**
     * 导出招投标列表
     */
    @PreAuthorize("@ss.hasPermi('bid:invite_tenders:export')")
    @Log(title = "招投标", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BidList bidList) {
        List<BidList> list = bidListService.selectBidListList(bidList);
        ExcelUtil<BidList> util = new ExcelUtil<BidList>(BidList.class);
        util.exportExcel(response, list, "招投标数据");
    }

    /**
     * 获取招投标详细信息
     */
//    @PreAuthorize("@ss.hasPermi('bid:invite_tenders:query')")
    @GetMapping(value = "/{projId}")
    public AjaxResult getInfo(@PathVariable("projId") Long projId) {
        return success(bidListService.selectBidListByProjId(projId));
    }

    /**
     * 新增招投标
     */
    @PreAuthorize("@ss.hasPermi('bid:invite_tenders:add')")
    @Log(title = "招投标", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BidList bidList) {
        return toAjax(bidListService.insertBidList(bidList));
    }

    /**
     * 修改招投标
     */
    @PreAuthorize("@ss.hasPermi('bid:invite_tenders:edit')")
    @Log(title = "招投标", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BidList bidList) {
        return toAjax(bidListService.updateBidList(bidList));
    }

    /**
     * 删除招投标
     */
    @PreAuthorize("@ss.hasPermi('bid:invite_tenders:remove')")
    @Log(title = "招投标", businessType = BusinessType.DELETE)
    @DeleteMapping("/{projIds}")
    public AjaxResult remove(@PathVariable Long[] projIds) {
        return toAjax(bidListService.deleteBidListByProjIds(projIds));
    }

    //state++
    @PreAuthorize("@ss.hasPermi('bid:invite_tenders:edit')")
    @Log(title = "招投标", businessType = BusinessType.UPDATE)
    @PutMapping("/lssue")
    public AjaxResult lssue(@RequestBody BidList bidList) {
        Date date = new Date();
//        if (date.compareTo(bidList.getProjEnd())>0){
//            return AjaxResult.error("超过截止发售时间");
//        }
        return bidListService.updateByStatus(bidList);
    }

    @PreAuthorize("@ss.hasPermi('bid:invite_tenders:query')")
    @GetMapping("/uploadFile")
    @Log(title = "上传压缩文件", businessType = BusinessType.OTHER)
    public AjaxResult uploadFile(HttpServletRequest request) throws Exception {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

        //files对应前端的key
        MultipartFile multipartFile = multipartRequest.getFile("files");
        byte[] bytes = multipartFile.getBytes();//得到文件的二进制流
        return null;
    }

    @Log(title = "随机生成专家")
    @GetMapping("/randomExpert")
    public AjaxResult randomExpert(Integer data) {
        if (data == null) {
            return AjaxResult.error("请输入评标人数");
        }
        Map<Integer, SysUser> map = new HashMap();
        List<SysUser> sysUsers = userService.selectUserList(new SysUser());
        List<SysUser> userList = userService.selectByTender();
        List<Long> randomId = userList.stream().map(SysUser::getUserId).collect(Collectors.toList());
        List<Integer> collect = randomId.stream().map(new Function<Long, Integer>() {
            @Override
            public Integer apply(Long aLong) {
                return aLong.intValue();

            }
        }).collect(Collectors.toList());
        for (int i = 0; i < userList.size(); i++) {
            map.put(collect.get(i), userList.get(i));
        }
        List<SysUser> rest = new ArrayList<>();
        List<Integer> randoms = createRandoms(collect, data);
        for (int i = 0; i < randoms.size(); i++) {
            rest.add(map.get(randoms.get(i)));
        }
        for (SysUser sysUser : rest) {
            System.out.println(sysUser);
        }
        return AjaxResult.success(rest);

    }

    /**
     * 从集合中随机取出N个不重复的元素
     *
     * @param list 需要被取出数据的集合
     * @param n    取出的元素数量
     * @return
     */
    private static List<Integer> createRandoms(List<Integer> list, int n) {
        Map<Integer, String> map = new HashMap();
        List<Integer> news = new ArrayList();
        if (list.size() <= n) {
            return list;
        }
        else {
            while (map.size() < n) {
                int random = (int) (Math.random() * list.size());
                if (!map.containsKey(random)) {
                    map.put(random, "");
                    news.add(list.get(random));
                }
            }
            return news;
        }
    }

    @Transactional
    @PostMapping("/startEvaluation")
    public AjaxResult startEvaluation(@RequestBody Map map) {
        Integer projId = (Integer) map.get("projId");
        List<Integer> auth = (List) map.get("auth");
        String status = (String) map.get("status");
        Integer isGroupLeaders = (Integer) map.get("groupLeaders");

        //文件柜信息
        FolderStructure folderStructure = new FolderStructure();
        folderStructure.setParentId(3L);
        folderStructure.setProjId(projId + "");
        folderStructure.setFolderName(FolderStructureState.REVIEW_DOCUMENT.getType());
//        folderStructure.setFolderPath(map.get());
        folderStructure.setUpdateBy(SecurityUtils.getUsername());
        folderStructureService.insertFolderStructure(folderStructure);

        //文件夹信息
        FileInfomation fileInfomation = new FileInfomation();
        fileInfomation.setProjId(projId.longValue());
        fileInfomation.setFolderId(folderStructure.getFolderId());
//        pingbiaowj.substring(pingbiaowj.lastIndexOf("/") + 1)
        String pingbiaowj = map.get("pingbiaowj").toString();
        fileInfomation.setFileName(pingbiaowj);
        fileInfomation.setCreateBy(SecurityUtils.getUsername());
        fileInfomationService.insertFileInfomation(fileInfomation);

        EvaluationExpertsInformation evaluationExpertsInformation = create(projId.longValue(), Long.valueOf(auth.get(0)), fileInfomation, Integer.valueOf(status), isGroupLeaders.longValue());
        EvaluationExpertsInformation evaluationExpertsInformation1 = create(projId.longValue(), Long.valueOf(auth.get(1)), fileInfomation, Integer.valueOf(status), isGroupLeaders.longValue());

        //新增
        iEvaluationExpertsInformationService.insertEvaluationExpertsInformation(evaluationExpertsInformation);
        iEvaluationExpertsInformationService.insertEvaluationExpertsInformation(evaluationExpertsInformation1);

        return AjaxResult.success();
    }

    public EvaluationExpertsInformation create(Long projId, Long auth, FileInfomation fileInfomation, Integer status, Long isGroupLeaders) {
        EvaluationExpertsInformation evaluationExpertsInformation = new EvaluationExpertsInformation();
        evaluationExpertsInformation.setProjId(projId);
        evaluationExpertsInformation.setUserId(auth);
        evaluationExpertsInformation.setFileId(fileInfomation.getFileId());
        evaluationExpertsInformation.setCreateBy(SecurityUtils.getUsername());
        evaluationExpertsInformation.setBidStatus(status);
        int i = isGroupLeaders.equals(auth) ? 1 : 0;
        System.out.println(i);
        evaluationExpertsInformation.setIsGroupLeaders(i + "");

        return evaluationExpertsInformation;
    }


}
