package com.ld.intercity.business.wallet.mapper;

import com.ld.intercity.business.wallet.model.WalletRecordModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface WalletRecordMapper {
    @Insert("insert into wallet_record_table (uuid,wallet_id,create_by,create_date,amount,card_number,status) " +
            "values (#{w.uuid},#{w.walletId},#{w.createBy},#{w.createDate},#{w.amount},#{w.cardNumber},#{w.status})")
    int saveWalletRecord(@Param("w") WalletRecordModel walletRecordModel);

    @Select("select uuid,wallet_id as walletId,create_by as createBy,create_date as createDate,amount," +
            "card_number as cardNumber,status from wallet_record_table where status = #{status}")
    List<WalletRecordModel> findAllWalletRecord(@Param("status") String status);

    @Update("update wallet_record_table set status = '1' where uuid = #{walletRecordId}")
    int walletRecordComplete(@Param("walletRecordId") String walletRecordId);

    @Select("select uuid,wallet_id as walletId,create_by as createBy,create_date as createDate,amount," +
            "card_number as cardNumber,status from wallet_record_table where uuid = #{walletRecordId}")
    WalletRecordModel getWalletRecordById(@Param("walletRecordId") String walletRecordId);
}
