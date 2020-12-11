package com.ld.intercity.business.wallet.mapper;

import com.ld.intercity.business.wallet.model.WalletModel;
import org.apache.ibatis.annotations.*;

@Mapper
public interface WalletMapper {
    /**
     * 根據用户ID查询账户
     * @param userId
     * @return
     */
    @Select("select uuid,user_id as userId,wallet_amount as walletAmount from wallet_table where user_id = #{userId}")
    WalletModel getByUserId(@Param("userId") String userId);

    @Update("update wallet_table set wallet_amount = #{wallet.walletAmount} where user_id = #{wallet.userId}")
    int update(@Param("wallet") WalletModel walletModel);

    @Insert("insert into wallet_table values (#{wallet.uuid},#{wallet.userId},#{wallet.walletAmount})")
    int save(@Param("wallet") WalletModel walletModel);
}
