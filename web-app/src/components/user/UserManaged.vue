<template lang="html">
  <div>
  <!--查尋條件-->
  <el-row>
  <el-form :inline="true" :model="form" class="demo-form-inline">
  <el-form-item label="用户名">
    <el-input v-model="form.username" placeholder="用户名"></el-input>
  </el-form-item>
  <el-form-item label="手机号码">
    <el-input v-model="form.phone" placeholder="手机号码"></el-input>
  </el-form-item>
  <el-form-item>
    <el-button type="primary" @click="query">查询</el-button>
  </el-form-item>
  </el-form>
  </el-row>
  <el-row style="margin-bottom: 20px">
  <el-button type="primary" icon="el-icon-add">主要按钮</el-button>
  <el-button type="warning" icon="el-icon-setting">警告按钮</el-button>
  <el-button type="danger" icon="el-icon-delete">危险按钮</el-button>
  </el-row>

  <el-table
    :data="tableData3"
    v-loading="loading"
    element-loading-text="加载中"
    @selection-change="handleSelectionChange"
    style="width: 100%; margin-top: 10px">
     <el-table-column
      type="selection"
      width="55">
    </el-table-column>
    <el-table-column
      prop="date"
      label="日期"
      width="180">
    </el-table-column>
    <el-table-column
      prop="name"
      label="姓名"
      width="180">
    </el-table-column>
    <el-table-column
      prop="address"
      label="地址">
    </el-table-column>
    <el-table-column
      fixed="right"
      label="操作"
      width="300">
      <template slot-scope="scope" >
        <el-button @click="handleClick(scope.row)" size="small">查看</el-button>
        <el-button type="warning" size="small">编辑</el-button>
        <el-button type="danger" size="small">编辑</el-button>
      </template>
    </el-table-column>
  </el-table>

  <el-row style="text-align: center; margin-top: 20px">
  <el-col :span="24">
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="currentPage4"
      :page-sizes="[5, 10, 20, 50]"
      :page-size="100"
      layout="total, sizes, prev, pager, next, jumper"
      :total="400">
    </el-pagination>
  </el-col>
  </el-row>
  </div>
</template>

<script>
export default {
  created () {
    // 加载表格数据
  },
  data () {
    return {
      form: {
        username: null,
        phone: null,
        pageNum: 1,
        pageSize: 10
      },
      tableData3: [{
        date: '2016-05-03',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }, {
        date: '2016-05-02',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }, {
        date: '2016-05-04',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }, {
        date: '2016-05-01',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }, {
        date: '2016-05-08',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }, {
        date: '2016-05-06',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }, {
        date: '2016-05-07',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }]
    }
  },
  methods: {
    query () {},
    loadTable () {

    }
  }
}
</script>
