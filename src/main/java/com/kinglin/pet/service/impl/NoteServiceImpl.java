package com.kinglin.pet.service.impl;

import com.kinglin.pet.entity.Note;
import com.kinglin.pet.dao.NoteMapper;
import com.kinglin.pet.service.NoteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 事件记录表 服务实现类
 * </p>
 *
 * @author huangjl
 * @since 2024-10-15
 */
@Service
public class NoteServiceImpl extends ServiceImpl<NoteMapper, Note> implements NoteService {

}
