package com.datasource.demo.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.datasource.demo.entity.gas.*;
import com.datasource.demo.entity.mydb.*;
import com.datasource.demo.service.gas.*;
import com.datasource.demo.service.mydb.*;
import com.datasource.demo.utils.MoneyUtil;
import lombok.extern.slf4j.Slf4j;
import org.nutz.lang.Lang;
import org.nutz.lang.Times;
import org.nutz.lang.util.NutMap;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @title: IdenxServer
 * @description:
 * @date: 2020/8/17
 * @author: zwh
 * @copyright: Copyright (c) 2020
 * @version:
 */

@Service
@Slf4j
public class IndexServer {
    static final String PATH_FORMAT1 = "yyyy-MM-dd HH:mm:ss";
    static final String PATH_FORMAT2 = "yyyyMMddHHmmss";

    @Autowired
    GasAreaCommunityService gasAreaCommunityService;
    @Autowired
    ConsumerService consumerService;
    @Autowired
    GasBookNoService gasBookNoService;
    @Autowired
    CommunityService communityService;
    @Autowired
    UsercardinfoService usercardinfoService;
    @Autowired
    CardoprecordService cardoprecordService;
    @Autowired
    MakeupcardService makeupcardService;
    @Autowired
    GasUserService gasUserService;
    @Autowired
    GasMendGasService gasMendGasService;
    @Autowired
    GasMeterService gasMeterService;
    @Autowired
    GasUserChargeRecordService gasUserChargeRecordService;
    @Autowired
    GasRefundGasService gasRefundGasService;
    @Autowired
    GasUserChargeRecord2019Service userChargeRecord2019Service;
    @Autowired
    GasUserChargeRecord2020Service userChargeRecord2020Service;

    public String index() {
        GasUserEntity gasUserEntity=gasUserService.getById("JF" + addZeroForNum(String.valueOf(1), 8));
        return JSONObject.toJSONString(gasUserEntity);
    }

    /**
     * @description: 同步小区
     * @date: 2020/8/18
     * @author: zwh
     */
    @Transactional(rollbackFor = Exception.class)
    public String syncComm() {
        AtomicInteger areaCount= new AtomicInteger();
        AtomicInteger bookCount= new AtomicInteger();
        List<CommunityEntity> list = communityService.list();
        // 获取本号最大值
        list.forEach(item->{
            GasBookNoEntity books = gasBookNoService.getOne(new QueryWrapper<GasBookNoEntity>().lt("id", 7000).orderByDesc("id").last("limit 1"));
            GasAreaCommunityEntity areaCommunity=new GasAreaCommunityEntity();
            areaCommunity.setId(getUUID());
            areaCommunity.setName(item.getCommName()+"(金凤系统小区)");
            areaCommunity.setAddress(areaCommunity.getName());
            areaCommunity.setOldKeywords(areaCommunity.getName());
            areaCommunity.setOpBy("system import");
            areaCommunity.setOpAt(Times.getTS());
            areaCommunity.setDelFlag(false);
            areaCommunity.setAreaCode("150203");
            GasBookNoEntity bookNoEntity=new GasBookNoEntity();
            bookNoEntity.setId(books.getId()+1);
            bookNoEntity.setAddress(areaCommunity.getName());
            bookNoEntity.setCommunityId(areaCommunity.getId());
            bookNoEntity.setDistributed(true);
            bookNoEntity.setOpBy("system import");
            bookNoEntity.setOpAt(Times.getTS());
            bookNoEntity.setDelFlag(false);
            // 查询小区下面的人数
            List<ConsumerEntity> consumer = consumerService.list(new QueryWrapper<ConsumerEntity>().eq("Community_Comm_id", areaCommunity.getId()));
            bookNoEntity.setUserCount(consumer.size());
            boolean area=gasAreaCommunityService.saveOrUpdate(areaCommunity);
            if(!area){
                log.error("失败:{}", areaCommunity.getId());
            }else {
                areaCount.getAndIncrement();
            }
            boolean book=gasBookNoService.saveOrUpdate(bookNoEntity);
            if(!book){
                log.error("失败:{}", bookNoEntity.getId());
            }else {
                bookCount.getAndIncrement();
            }
        });
        String res="小区成功:"+areaCount+"本号成功:"+bookCount;
        String count="小区总数:"+list.size()+"本号总数:"+list.size();
        return res+"====="+count;
    }


    /**
     * @description: 同步方法信息不包含小区
     * @date: 2020/8/16
     * @author: zwh
     */
    @Transactional(rollbackFor = Exception.class)
    public void syncInfo(Integer id) {
        try {
            AtomicInteger count = new AtomicInteger();
            AtomicInteger meterCount = new AtomicInteger();
            AtomicInteger buyGasCount = new AtomicInteger();
            AtomicInteger mendGasCount = new AtomicInteger();
            AtomicInteger refundGasCount = new AtomicInteger();
            List<GasUserChargeRecordEntity> chargeRecordList = new ArrayList<>();
            List<GasMendGasEntity> mendGasList = new ArrayList<>();
            List<GasRefundGasEntity> refundGasList = new ArrayList<>();
            // 系统内部小区
            Map<String, Object> gasComm = gasAreaCommunityService.getListAreaToMap();
            // 金凤小区
            Map<Integer, String> jfComm = communityService.getOneCommunity();
            // 获取金凤库中人员信息
            List<ConsumerEntity> consumerEntities = new ArrayList<>();
            if (id != null && id != 0) {
                consumerEntities = consumerService.list(new QueryWrapper<ConsumerEntity>().eq("CSM_id", id).orderByAsc("CSM_id"));
            } else {
                consumerEntities = consumerService.list(new QueryWrapper<ConsumerEntity>().orderByAsc("CSM_id"));
            }

            if (Lang.isEmpty(consumerEntities)) {
                return;
            }
            List<ConsumerEntity> consumerEntities1 =new ArrayList<>();
            if(consumerEntities.size()>5){
                consumerEntities1=consumerEntities.subList(0,5);
            }else {
               consumerEntities1=consumerEntities.subList(0,consumerEntities.size());
            }
            consumerEntities1.forEach(item -> {
                UsercardinfoEntity cardInfo = usercardinfoService.getById(item.getCsmId());
                // 金凤库购气+补气次数
                /*List<CardoprecordEntity> buyJf = cardoprecordService.list(new QueryWrapper<CardoprecordEntity>()
                        .eq("CO_ConsumerId", item.getCsmId()).gt("CO_WaterCount", 0).gt("CO_Freewater", 0));*/
                // 购气、退气、补气全记录
                List<CardoprecordEntity> jfAcc = cardoprecordService.list(new QueryWrapper<CardoprecordEntity>()
                        .eq("CO_ConsumerId", item.getCsmId()).and(i -> i.ne("CO_WaterCount", 0).or().ne("CO_Freewater", 0)));
                // 金凤库充值记录
                List<CardoprecordEntity> cardCord = cardoprecordService.list(new QueryWrapper<CardoprecordEntity>()
                        .eq("CO_ConsumerId", item.getCsmId()).gt("CO_WaterCount", 0).orderByAsc("CO_id"));
                // 退气记录
                List<CardoprecordEntity> reFundCord = cardoprecordService.list(new QueryWrapper<CardoprecordEntity>()
                        .eq("CO_ConsumerId", item.getCsmId()).lt("CO_WaterCount", 0).orderByAsc("CO_id"));
                // 金凤 库补卡
                int mendCount = makeupcardService.count(new QueryWrapper<MakeupcardEntity>()
                        .eq("MUC_ConsumerId", item.getCsmId()).eq("MUC_OPType", 3));
                // 上次充值时间金额
                long lastRecTime = 0L;
                long lastRecMoney = 0L;
                if (cardCord != null && cardCord.size() > 0) {
                    lastRecTime = cardCord.get(cardCord.size() - 1).getCoCreatetime().toEpochSecond(ZoneOffset.of("+8"));
                    lastRecMoney = MoneyUtil.yuanToLi(String.valueOf(cardCord.get(cardCord.size() - 1).getCoTotalnormalfee()));
                }
                // fixme 累计购气量  购气+退气
                int buyGasJfSum = cardCord.stream().mapToInt(CardoprecordEntity::getCoWatercount).sum();
                int fefundJfSum = reFundCord.stream().mapToInt(CardoprecordEntity::getCoWatercount).sum();
                int jfSumCount = buyGasJfSum + fefundJfSum;
                // fixme 累计购气金额 购气+退气
                long buyMoney = MoneyUtil.yuanToLi(String.valueOf(cardCord.stream().mapToDouble(CardoprecordEntity::getCoTotalnormalfee).sum()));
                long refundMoney = MoneyUtil.yuanToLi(String.valueOf(reFundCord.stream().mapToDouble(CardoprecordEntity::getCoTotalnormalfee).sum()));
                long jfSumMoney = buyMoney + refundMoney;
                // 购气次数
                /*int jfCount=0;
                if(buyJf!=null){
                    int refund=reFundCord==null?0:reFundCord.size();
                    jfCount=buyJf.size()-refund;
                }*/
                // 用户是否存在
                GasUserEntity gasUser=gasUserService.getById("JF" + addZeroForNum(String.valueOf(item.getCsmId()), 8));
                if(Lang.isEmpty(gasUser)){
                    gasUser.setAccountNumber("JF" + addZeroForNum(String.valueOf(item.getCsmId()), 8));
                    gasUser.setSysNumber("03");
                    gasUser.setOldAccountNumber("");
                    // 小区名称
                    if (keyAndValue(jfComm.get(item.getCommunityCommId())+"(金凤系统小区)", gasComm)) {
                        gasUser.setCommunity(getKey(gasComm, jfComm.get(item.getCommunityCommId())).get(0));
                        GasBookNoEntity bookNoEntity=gasBookNoService.getBooks(gasUser.getCommunity());
                        Integer bookNo = Lang.isEmpty(bookNoEntity) ? 1 : bookNoEntity.getId()+1;
                        gasUser.setBookNo(String.valueOf(bookNo));
                    }
                    gasUser.setAccountNo(0);//补户号
                    gasUser.setAccountName(item.getCsmName());
                    gasUser.setBalance(MoneyUtil.yuanToLi(String.valueOf(cardInfo.getCardTodayRemainMoney())));
                    gasUser.setArrears(0L);
                    gasUser.setLateFee(0L);
                    gasUser.setUseType(cnbt_price_map.get(cardInfo.getWaterpriceWpId()));//用气类型
                    gasUser.setMobile(item.getCsmTel1());
                    gasUser.setAddress(item.getCsmAddr());
                    gasUser.setUserType(1);
                    gasUser.setMeterId(getUUID());
                    gasUser.setOpenTime(item.getCsmOpentime().toEpochSecond(ZoneOffset.of("+8")));
                    gasUser.setAddGas(BigDecimal.valueOf(0));
                    gasUser.setStatus(1);
                    gasUser.setDelFlag(item.getCsmIsdeleted() == 0 ? false : true);
                    gasUser.setCanDelete(false);
                    gasUser.setHeatingBand(0);
                    gasUser.setBatchOpen(0);
                    gasUser.setLastRechargeTime(lastRecTime);//上次充值时间
                    gasUser.setLastRechargeMoney(lastRecMoney);//上次充值金额
                    gasUser.setOpenCard(item.getCsmIsopened());
                    gasUser.setTotalDosage(BigDecimal.valueOf(jfSumCount));
                    gasUser.setTotalBuyGasMoney(jfSumMoney);
                    //  用户入库
                    boolean user = gasUserService.insetUser(gasUser);
                    if (!user) {
                        log.error("失败:{}", gasUser.getAccountNumber());
                    } else {
                        count.getAndIncrement();
                    }
                }
                GasMeterEntity gasMeter = new GasMeterEntity();
                //  fixme   meter 重复执行   
                gasMeter.setId((gasUser.getMeterId()));
                gasMeter.setMeterNo("");
                gasMeter.setSupplier("JF");
                // fixme  平台创建
                gasMeter.setModelId("7f5ad25e1ea7410da7bdd1296e335f6d");
                gasMeter.setDigit(0);
                gasMeter.setDelFlag(false);
                gasMeter.setCardNo(String.valueOf(cardInfo.getCardId()));
                gasMeter.setInstallTime(cardInfo.getCardCreatetime().toEpochSecond(ZoneOffset.of("+8")));
                gasMeter.setOpenCard(item.getCsmIsopened());
                gasMeter.setOpenCardTime(item.getCsmOpentime().toEpochSecond(ZoneOffset.of("+8")));
                gasMeter.setBuyGasCount(cardInfo.getCardBuycount());
                gasMeter.setMendCardCount(mendCount);
                // 表具入库
                boolean meter = gasMeterService.insertMeter(gasMeter);
                if (!meter) {
                    log.error("表具：{}", gasMeter.getId());
                } else {
                    meterCount.getAndIncrement();
                }
                // 购气记录

               /* cardCord.forEach(buyGas->{
                    GasUserChargeRecordEntity gasUserCharge=new GasUserChargeRecordEntity();
                    gasUserCharge.setId("JF"+buyGas.getCoId());
                    gasUserCharge.setAccountNumber(String.valueOf(buyGas.getCoConsumerid()));
                    gasUserCharge.setMeterId(gasUser.getMeterId());
                    gasUserCharge.setPaidAmount(MoneyUtil.yuanToLi(String.valueOf(buyGas.getCoTotalnormalfee())));
                    gasUserCharge.setChangeMoney(0L);
                    gasUserCharge.setArrears(0L);
                    gasUserCharge.setBalance(MoneyUtil.yuanToLi(String.valueOf(buyGas.getCoLastremainmoney())));
                    gasUserCharge.setUserNewMoney(MoneyUtil.yuanToLi(String.valueOf(buyGas.getCoTodayremainmoney())));
                    gasUserCharge.setPreStored(MoneyUtil.yuanToLi(String.valueOf(buyGas.getCoTodayremainmoney())));
                    gasUserCharge.setPayMethod(0);
                    gasUserCharge.setPaySource(2);
                    gasUserCharge.setUseType(gasUser.getUseType());
                    gasUserCharge.setUserType(1);
                    gasUserCharge.setBuyGas(BigDecimal.valueOf(buyGas.getCoWatercount()));
                    gasUserCharge.setBuyGasAmount(MoneyUtil.yuanToLi(String.valueOf(buyGas.getCoTotalnormalfee())));
                    // 购气次数
                    gasUserCharge.setBuyGasTimes(Long.valueOf(gasMeter.getBuyGasCount()));
                    gasUserCharge.setTotalBuyGas(BigDecimal.valueOf(buyGasJfSum));
                    gasUserCharge.setTotalBuyGasAmount(buyMoney);
                    // 费用计算明细
                    long price = MoneyUtil.yuanToLi(String.valueOf(buyGas.getCoUnitnormalfee()));//单价
                    NutMap feeDetailMap = NutMap.NEW().setv("ladder", false);
                    List<NutMap> ladderConfig = new ArrayList<>();
                    ladderConfig.add(NutMap.NEW().setv("price", price).setv("dosage", ""));
                    ladderConfig.add(NutMap.NEW());
                    ladderConfig.add(NutMap.NEW());
                    ladderConfig.add(NutMap.NEW());
                    ladderConfig.add(NutMap.NEW());
                    feeDetailMap.setv("ladderConfig", ladderConfig);
                    feeDetailMap.setv("gasValue", gasUserCharge.getBuyGas());
                    List<NutMap> detail = new ArrayList<>();
                    detail.add(NutMap.NEW().setv("price", price).setv("cur", 0).setv("dosage", gasUserCharge.getBuyGas()).setv("fee", gasUserCharge.getBuyGasAmount()));
                    feeDetailMap.setv("detail", detail);
                    feeDetailMap.setv("fee", gasUserCharge.getBuyGasAmount());
                    gasUserCharge.setFeeDetail(JSON.toJSONString(feeDetailMap));
                    gasUserCharge.setPayStatus(2);
                    gasUserCharge.setPayTime(buyGas.getCoCreatetime().toEpochSecond(ZoneOffset.of("+8")));
                    gasUserCharge.setStatus(0);
                    gasUserCharge.setSettleStatus(1);
                    gasUserCharge.setSettleTime(buyGas.getCoCreatetime().toEpochSecond(ZoneOffset.of("+8")));
                    gasUserCharge.setSettleByName("系统");
                    gasUserCharge.setOpByName("系统");
                    gasUserCharge.setNote(buyGas.getCoRemark());
                    chargeRecordList.add(gasUserCharge);
                });*/
                jfAcc.forEach(accGas -> {
                    // 购气
                    if (accGas.getCoWatercount() > 0) {
                        GasUserChargeRecordEntity gasUserCharge = new GasUserChargeRecordEntity();
                        // 流水号
                        gasUserCharge.setId(accNo(accGas.getCoCreatetime(), accGas.getCoId()));
                        gasUserCharge.setAccountNumber(gasUser.getAccountNumber());
                        gasUserCharge.setMeterId(gasUser.getMeterId());
                        gasUserCharge.setPaidAmount(MoneyUtil.yuanToLi(String.valueOf(accGas.getCoTotalnormalfee())));
                        gasUserCharge.setChangeMoney(0L);
                        gasUserCharge.setArrears(0L);
                        gasUserCharge.setBalance(MoneyUtil.yuanToLi(String.valueOf(accGas.getCoLastremainmoney())));
                        gasUserCharge.setUserNewMoney(MoneyUtil.yuanToLi(String.valueOf(accGas.getCoTodayremainmoney())));
                        gasUserCharge.setPreStored(MoneyUtil.yuanToLi(String.valueOf(accGas.getCoTodayremainmoney())));
                        gasUserCharge.setPayMethod(0);
                        gasUserCharge.setPaySource(2);
                        gasUserCharge.setUseType(gasUser.getUseType());
                        gasUserCharge.setDelFlag(false);
                        gasUserCharge.setUserType(1);
                        gasUserCharge.setBuyGas(BigDecimal.valueOf(accGas.getCoWatercount()));
                        gasUserCharge.setBuyGasAmount(MoneyUtil.yuanToLi(String.valueOf(accGas.getCoTotalnormalfee())));
                        // 购气次数
                        gasUserCharge.setBuyGasTimes(Long.valueOf(gasMeter.getBuyGasCount()));
                        gasUserCharge.setTotalBuyGas(BigDecimal.valueOf(buyGasJfSum));
                        gasUserCharge.setTotalBuyGasAmount(buyMoney);
                        // 费用计算明细
                        long price = MoneyUtil.yuanToLi(String.valueOf(accGas.getCoUnitnormalfee()));//单价
                        NutMap feeDetailMap = NutMap.NEW().setv("ladder", false);
                        List<NutMap> ladderConfig = new ArrayList<>();
                        ladderConfig.add(NutMap.NEW().setv("price", price).setv("dosage", ""));
                        ladderConfig.add(NutMap.NEW());
                        ladderConfig.add(NutMap.NEW());
                        ladderConfig.add(NutMap.NEW());
                        ladderConfig.add(NutMap.NEW());
                        feeDetailMap.setv("ladderConfig", ladderConfig);
                        feeDetailMap.setv("gasValue", gasUserCharge.getBuyGas());
                        List<NutMap> detail = new ArrayList<>();
                        detail.add(NutMap.NEW().setv("price", price).setv("cur", 0).setv("dosage", gasUserCharge.getBuyGas()).setv("fee", gasUserCharge.getBuyGasAmount()));
                        feeDetailMap.setv("detail", detail);
                        feeDetailMap.setv("fee", gasUserCharge.getBuyGasAmount());
                        gasUserCharge.setFeeDetail(JSON.toJSONString(feeDetailMap));
                        gasUserCharge.setPayStatus(2);
                        gasUserCharge.setPayTime(accGas.getCoCreatetime().toEpochSecond(ZoneOffset.of("+8")));
                        gasUserCharge.setStatus(0);
                        gasUserCharge.setDelFlag(false);
                        gasUserCharge.setSettleStatus(1);
                        gasUserCharge.setSettleTime(accGas.getCoCreatetime().toEpochSecond(ZoneOffset.of("+8")));
                        gasUserCharge.setSettleByName("系统");
                        gasUserCharge.setOpByName("系统");
                        gasUserCharge.setNote(accGas.getCoRemark());
                        //购气入库
                        if (("2019").equals(accGas.getCoCreatetime().toString().substring(0, 4))) {
                            GasUserChargeRecord2019Entity uc2019 = new GasUserChargeRecord2019Entity();
                            copyProperties(gasUserCharge, uc2019);
                            boolean uc1 = userChargeRecord2019Service.saveOrUpdate(uc2019);
                            if (!uc1) {
                                log.error("表具：{}", JSONObject.toJSONString(uc2019));
                            } else {
                                buyGasCount.getAndIncrement();
                            }
                        } else if (("2020").equals(accGas.getCoCreatetime().toString().substring(0, 4))) {
                            GasUserChargeRecord2020Entity uc2020 = new GasUserChargeRecord2020Entity();
                            copyProperties(gasUserCharge, uc2020);
                            boolean uc2 = userChargeRecord2020Service.saveOrUpdate(uc2020);
                            if (!uc2) {
                                log.error("表具：{}", JSONObject.toJSONString(uc2020));
                            } else {
                                buyGasCount.getAndIncrement();
                            }
                        }
                        chargeRecordList.add(gasUserCharge);
                    }
                    //补气
                    if (accGas.getCoWatercount() >= 0 && accGas.getCoFreewater() > 0) {
                        GasMendGasEntity gasMendGasEntity = new GasMendGasEntity();
                        gasMendGasEntity.setId(accNo(accGas.getCoCreatetime(), accGas.getCoId()));
                        gasMendGasEntity.setAccountNumber(gasUser.getAccountNumber());
                        gasMendGasEntity.setMeterId(gasUser.getMeterId());
                        gasMendGasEntity.setUseType(gasUser.getUseType());
                        gasMendGasEntity.setUserType(gasUser.getUserType());
                        gasMendGasEntity.setMendGas(BigDecimal.valueOf(accGas.getCoFreewater()));
                        gasMendGasEntity.setMendGasAmount(0L);
                        long price = MoneyUtil.yuanToLi(String.valueOf(accGas.getCoUnitnormalfee()));//单价
                        gasMendGasEntity.setCostMoney(price * accGas.getCoFreewater());
                        gasMendGasEntity.setBuyGasTimes(gasMeter.getBuyGasCount());
                        gasMendGasEntity.setChargeType(0);
                        gasMendGasEntity.setFeeDetail("");
                        gasMendGasEntity.setDelFlag(false);
                        gasMendGasEntity.setCreatAt(accGas.getCoCreatetime().toEpochSecond(ZoneOffset.of("+8")));
                        gasMendGasEntity.setStatus(2);
                        gasMendGasEntity.setReason(accGas.getCoRemark());
                        gasMendGasEntity.setOpByName("系统");
                        // 补气入库
                        boolean insertMendGas = gasMendGasService.saveOrUpdate(gasMendGasEntity);
                        if (!insertMendGas) {
                            log.error("表具：{}", JSONObject.toJSONString(gasMendGasEntity));
                        }else {
                            mendGasCount.getAndIncrement();
                        }
                        mendGasList.add(gasMendGasEntity);
                    }
                    // 退气
                    if (accGas.getCoWatercount() < 0) {
                        GasRefundGasEntity gasRefundGasEntity = new GasRefundGasEntity();
                        gasRefundGasEntity.setId(accNo(accGas.getCoCreatetime(), accGas.getCoId()));
                        gasRefundGasEntity.setAccountNumber(gasUser.getAccountNumber());
                        gasRefundGasEntity.setMeterId(gasUser.getMeterId());
                        gasRefundGasEntity.setUseType(gasUser.getUseType());
                        gasRefundGasEntity.setUserType(gasUser.getUserType());
                        gasRefundGasEntity.setRefundGas(BigDecimal.valueOf(Math.abs(accGas.getCoWatercount()) + accGas.getCoFreewater()));
                        gasRefundGasEntity.setRefundGasMoney(0L);
                        gasRefundGasEntity.setRefundMoney(Math.abs(MoneyUtil.yuanToLi(String.valueOf(accGas.getCoTotalnormalfee()))));//气费金额
                        gasRefundGasEntity.setChargeType(0);
                        gasRefundGasEntity.setChargeRecordId("");
                        gasRefundGasEntity.setStatus(2);
                        gasRefundGasEntity.setDelFlag(false);
                        gasRefundGasEntity.setCreatAt(accGas.getCoCreatetime().toEpochSecond(ZoneOffset.of("+8")));
                        gasRefundGasEntity.setReason(accGas.getCoRemark());
                        gasRefundGasEntity.setOpByName("系统");
                        // 退气入库
                        boolean refund1 = gasRefundGasService.saveOrUpdate(gasRefundGasEntity);
                        if (!refund1) {
                            log.error("表具：{}", JSONObject.toJSONString(gasRefundGasEntity));
                        }else {
                            refundGasCount.getAndIncrement();
                        }
                        refundGasList.add(gasRefundGasEntity);
                    }
                });
            });
            log.info("用户成功{}条,水表成功{}条,购气成功{}条,补气成功{}条，退气成功{}条",
                    count,meterCount,buyGasCount,mendGasCount,refundGasCount);
            log.info("用户总{}条,水表总{}条,购气总{}条,补气总{}条，退气总{}条",
                    consumerEntities.size(),consumerEntities.size(),chargeRecordList.size(),
                    mendGasList.size(),refundGasList.size());
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("异常：{}", e.getMessage());
        }
    }

    // 金凤 用气类型
    private static Map<Integer, String> cnbt_price_map = new HashMap() {
        {
            put(3, "F_M_0");//居民煤气0.8
            put(4, "F_M_1");//居民天然气2.06
            put(5, "F_G_4");// add  商业天然气 2.3
            put(6, "F_G_1");//公用煤气1.2
        }
    };
    // 金凤 单价
    private static Map<String, Long> jf_price_map = new HashMap() {
        {
            put("F_M_0", 800);//居民煤气0.8
            put("F_M_1", 2060);//居民天然气2.06
            put("F_G_4", 2300);// add  商业天然气 2.3
            put("F_G_1", 1200);//公用煤气1.2
        }
    };

    // Map比较
    public boolean keyAndValue(String val, Map<String, Object> map2) {
        return map2.containsValue(val);
    }

    // val -> key
    public static List<String> getKey(Map<String, Object> map, Object value) {
        List<String> keyList = new ArrayList<>();
        for (String key : map.keySet()) {
            if (map.get(key).equals(value)) {
                keyList.add(key);
            }
        }
        return keyList;
    }

    // 时间
    private long parseDate(String dateStr) {
        long openTime = 0;
        try {
            Date openDate = Times.parse("yyyy-MM-dd HH:mm:ss", dateStr);
            openTime = openDate.getTime() / 1000;
        } catch (Exception e) {
            try {
                Date openDate = Times.D(dateStr.substring(0, 10));
                openTime = openDate.getTime() / 1000;
            } catch (Exception exception) {
                openTime = 1451606400L;//保底，交易记录中最早的一条都是2016，2016-01-01 08:00:00
            }
        }
        return openTime;
    }

    // 时间格式 转换
    public static String getTimeToString(String str) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(PATH_FORMAT1);
        SimpleDateFormat df = new SimpleDateFormat(PATH_FORMAT2);
        try {
            return df.format(dateFormat.parse(str));
        } catch (ParseException e) {
            return df.format(new Date());
        }
    }

    // 左补0
    public static String addZeroForNum(String str, int strLength) {
        int strLen = str.length();
        StringBuffer sb = null;
        while (strLen < strLength) {
            sb = new StringBuffer();
            sb.append("0").append(str);
            str = sb.toString();
            strLen = str.length();
        }
        return str;
    }

    // 购气、补气、退气单号
    public static String accNo(LocalDateTime localDateTime, Integer accId) {
        String str = String.valueOf(localDateTime).replace("T", " ");
        return "JF" + getTimeToString(str) + addZeroForNum(String.valueOf(accId), 5);
    }

    // bean copy
    public static void copyProperties(Object source, Object target) {
        try {
            BeanUtils.copyProperties(source, target);
        } catch (BeansException e) {
            log.error("BeanUtil property copy  failed :BeansException", e);
        } catch (Exception e) {
            log.error("BeanUtil property copy failed:Exception", e);
        }
    }
    // uuid
    public String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}