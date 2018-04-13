package com.peng.db.spring.boot.autoconfigure.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

public class BaseService<T> {

    @Autowired
    protected Mapper<T> mapper;

    /**
     * 查询所有
     */
    public List<T> selectAll() {
        return mapper.selectAll();
    }

    /**
     * 根据主键查询
     */
    public T selectByPrimaryKey(T key) {
        return mapper.selectByPrimaryKey(key);
    }

    /**
     * 根据条件实例查询
     */
    public List<T> selectByExample(Example example) {
        return mapper.selectByExample(example);
    }

    /**
     * 根据条件实例查询一个对象
     */
    public T selectOne(T record) {
        return mapper.selectOne(record);
    }

    /**
     * 新增
     */
    public int insert(T record) {
        return mapper.insert(record);
    }

    /**
     * 选择新增
     */
    public int insertSelective(T record) {
        return mapper.insertSelective(record);
    }

    /**
     * 根据主键新增
     */
    public int updateByPrimaryKey(T record) {
        return mapper.updateByPrimaryKey(record);
    }

    /**
     * 根据主键选择新增
     */
    public int updateByPrimaryKeySelective(T record) {
        return mapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 根据属性更新
     */
    public int updateByExample(T record, Example example) {
        return mapper.updateByExample(record, example);
    }

    /**
     * 根据属性选择更新
     */
    public int updateByExampleSelective(T record, Example example) {
        return mapper.updateByExampleSelective(record, example);
    }

    /**
     * 删除
     */
    public int delete(T record) {
        return mapper.delete(record);
    }


    /**
     * 根据主键删除
     */
    public int deleteByPrimaryKey(Object key) {
        return mapper.deleteByPrimaryKey(key);
    }

    /**
     * 批量删除
     */
    @Transactional
    public void deleteBatchByPrimaryKey(List<T> record) throws RuntimeException {
        record.forEach(it -> mapper.delete(it));
    }

    /**
     * 根据属性删除
     */
    public int deleteByExample(Example example) {
        return mapper.deleteByExample(example);
    }

    /**
     * 分页查询所有
     */
    public PageInfo<T> selectAllByList(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<T>(mapper.selectAll());
    }

    /**
     * 根据属性分页查询
     */
    public PageInfo<T> selectByList(T record, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<T>(mapper.select(record));
    }

    /**
     * 根据example分页查询
     */
    public PageInfo<T>  selectByExampleList(Example example, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(mapper.selectByExample(example));
    }

    /**
     * 分页查询所有
     */
    public PageInfo<T>  selectAllByOffSetList(Integer offset, Integer limit) {
        PageHelper.offsetPage(offset, limit);
        return new PageInfo<>(mapper.selectAll());
    }

    /**
     * 根据属性分页查询
     */
    public PageInfo<T> selectByOffSetList(T record, Integer offset, Integer limit) {
        PageHelper.offsetPage(offset, limit);
        return new PageInfo<>(mapper.select(record));
    }

    /**
     * 根据example分页查询
     */
    public PageInfo<T> selectByExampleOffSetList(Example example, Integer offset, Integer limit) {
        PageHelper.offsetPage(offset, limit);
        return new PageInfo<>(mapper.selectByExample(example));
    }
}
