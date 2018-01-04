package com.fs.service;

import com.openparts.common.pojo.Result;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public interface FileService {

	/**
	 * 上传文件，制定文件的格式
	 *
	 * @param inputStream
	 *            输入流
	 * @Param format 文件格式后缀
	 * @param uid
	 *            文件上传者
	 *
	 */
	public Result uploadFile(InputStream inputStream, String filename, String format, String uid);

	/**
	 * 图片上传
	 *
	 * @param inputStream
	 * @param returnSize
	 *            返回接近此size的url全路径
	 */
	public Result uploadImage(ByteArrayInputStream inputStream, String uid, Integer returnSize);

	/**
	 * 删除文件
	 *
	 * @param filename
	 * @return
	 */
	public Result delete(String filename);

	/**
	 * 获取文件
	 *
	 * @param filename
	 * @return
	 */
	public Result displayFile(String filename);

	public Result displayImage(String filename, String path);
}
