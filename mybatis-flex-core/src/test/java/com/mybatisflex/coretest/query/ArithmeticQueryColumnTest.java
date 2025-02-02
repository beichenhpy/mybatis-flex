/*
 *  Copyright (c) 2022-2023, Mybatis-Flex (fuhai999@gmail.com).
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.mybatisflex.coretest.query;

import com.mybatisflex.core.dialect.IDialect;
import com.mybatisflex.core.dialect.impl.CommonsDialectImpl;
import com.mybatisflex.core.query.QueryWrapper;
import org.junit.Assert;
import org.junit.Test;

import static com.mybatisflex.core.query.QueryMethods.sum;
import static com.mybatisflex.coretest.table.AccountTableDef.ACCOUNT;

public class ArithmeticQueryColumnTest {

    private static String toSql(QueryWrapper queryWrapper) {
        IDialect dialect = new CommonsDialectImpl();
        return dialect.forSelectByQuery(queryWrapper);
    }


    @Test
    public void testAdd() {
        QueryWrapper query = new QueryWrapper()
            .select(ACCOUNT.ID.add(100).as("x100"))
            .from(ACCOUNT);

        String sql = query.toSQL();
        System.out.println(sql);

        Assert.assertEquals(sql, "SELECT (`id` + 100) AS `x100` FROM `tb_account`");
    }

    @Test
    public void testAdd1() {
        QueryWrapper query = new QueryWrapper()
            .select(ACCOUNT.ID.add(100).add(200).add(300).as("x100"))
            .from(ACCOUNT);

        String sql = query.toSQL();
        System.out.println(sql);

        Assert.assertEquals(sql, "SELECT (`id` + 100 + 200 + 300) AS `x100` FROM `tb_account`");
    }

    @Test
    public void testAdd2() {
        QueryWrapper query = new QueryWrapper()
            .select(ACCOUNT.ID.add(ACCOUNT.AGE).as("x100"))
            .from(ACCOUNT);

        String sql = toSql(query);
        System.out.println(sql);

        Assert.assertEquals(sql, "SELECT (`id` + `age`) AS `x100` FROM `tb_account`");
    }

    @Test
    public void testAdd3() {
        QueryWrapper query = new QueryWrapper()
            .select(ACCOUNT.ID.add(ACCOUNT.AGE.add(100)).as("x100"))
            .from(ACCOUNT);

        String sql = query.toSQL();
        System.out.println(sql);

        Assert.assertEquals(sql, "SELECT (`id` + (`age` + 100)) AS `x100` FROM `tb_account`");
    }

    @Test
    public void testAdd4() {
        QueryWrapper query = new QueryWrapper()
            .select(ACCOUNT.ID.add(ACCOUNT.ID.add(100)).multiply(100).as("x100"))
            .from(ACCOUNT);

        String sql = toSql(query);
        System.out.println(sql);

        Assert.assertEquals(sql, "SELECT (`id` + (`id` + 100) * 100) AS `x100` FROM `tb_account`");
    }

    @Test
    public void testAdd5() {
        QueryWrapper query = new QueryWrapper()
            .select(sum(ACCOUNT.ID.multiply(ACCOUNT.AGE)).as("total_x"))
            .from(ACCOUNT);

        String sql = toSql(query);
        System.out.println(sql);

        Assert.assertEquals(sql, "SELECT SUM(`id` * `age`) AS `total_x` FROM `tb_account`");
    }

    @Test
    public void testSubtract() {
        QueryWrapper query = new QueryWrapper()
            .select(ACCOUNT.ID.subtract(100).as("x100"))
            .from(ACCOUNT);

        String sql = toSql(query);
        System.out.println(sql);

        Assert.assertEquals(sql, "SELECT (`id` - 100) AS `x100` FROM `tb_account`");
    }


    @Test
    public void testMultiply() {
        QueryWrapper query = new QueryWrapper()
            .select(ACCOUNT.ID.multiply(100).as("x100"))
            .from(ACCOUNT);

        String sql = toSql(query);
        System.out.println(sql);

        Assert.assertEquals(sql, "SELECT (`id` * 100) AS `x100` FROM `tb_account`");
    }


    @Test
    public void testDivide() {
        QueryWrapper query = new QueryWrapper()
            .select(ACCOUNT.ID.divide(100).as("x100"))
            .from(ACCOUNT);

        String sql = toSql(query);
        System.out.println(sql);

        Assert.assertEquals(sql, "SELECT (`id` / 100) AS `x100` FROM `tb_account`");
    }


}
