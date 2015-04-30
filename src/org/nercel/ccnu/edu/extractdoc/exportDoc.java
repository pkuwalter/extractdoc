//package org.nercel.ccnu.edu.extractdoc;
//
//
////需要import的包有如下
//import java.io.File;
//
//import com.aspose.words.Document;
//import com.aspose.words.DocumentBuilder;
//import com.aspose.words.ImageData;
//import com.aspose.words.Node;
//import com.aspose.words.NodeCollection;
//import com.aspose.words.NodeType;
//import com.aspose.words.Shape;
//import com.aspose.words.ShapeType;
//
///*aspose.words.for.java 版本 3.1.0
//
//将word文档中wmf图片导出并替换为标签
//
//@param fileName
//文档完整名
//
//@return 修改后的文件名
//*/
//
//public class exportDoc{
//	private String filePath;
//	private String imagePath;
//	
//	public String exportWmfFromDoc(String fileName) {
//		try {
//			Document doc = new Document(filePath + fileName);// 新建文档对象
//			NodeCollection shapeCollection = doc.getChildNodes(NodeType.SHAPE,
//					true);// 查询文档中所有wmf图片
//			Node[] shapes = shapeCollection.toArray();// 序列化
//			String imgPath = "";
//			if (shapes.length > 0) {// 如果文档存在图片
//				File file = new File(imagePath
//						+ fileName.substring(0, fileName.lastIndexOf(".")));
//				if (file != null) {
//					if (file.exists() || file.mkdir()) {// 创建文档图片保存文件夹
//						imgPath = file.getAbsolutePath() + "\\";
//					} else {
//						throw new Exception("文档图片保存路径不可写，请检查路径:\"" + imagePath
//								+ "\"");
//					}
//				}
//				for (Node node : shapes) {
//					Shape shape = (Shape) node;
//					if (shape.getShapeType() == ShapeType.OLE_OBJECT) {// 如果shape类型是ole类型
//						ImageData i = shape.getImageData();// 获得图片数据
//						String imageName = imageName() + ".wmf";
//						i.save(imgPath + imageName);// 导出图片
//						File f = new File(imgPath + imageName);
//						if (f.exists()) {
//							imageName = wmfToPNG(f.getAbsolutePath());
//							log.debug("f.path--->" + f.getAbsolutePath());
//							Exec.saveMinPhoto(imageName, imageName, (double)38, (double)0);
//							if (f.canWrite()) {
//								f.delete();
//							}
//						} else {
//							log.error("图片不存在！");
//							continue;
//						}
//						// log.info("f.name--->" + f.getName());
//						DocumentBuilder builder = new DocumentBuilder(doc);// 新建文档节点
//						builder.moveTo(shape);// 移动到图片位置
//						builder.write("[img]"
//								+ fileName.substring(0, fileName
//										.lastIndexOf("."))
//										+ "/"
//										+ f.getName().substring(0,
//												f.getName().lastIndexOf(".")) + ".png"
//												+ "[/img]");// 插入替换文本
//						shape.remove();// 移除图形
//					} else if (shape.getShapeType() == ShapeType.IMAGE) {// 如果shape类型是ole类型
//						ImageData i = shape.getImageData();// 获得图片数据
//						String imageName = imageName() + ".png";
//						i.save(imgPath + imageName);// 导出图片
//						File f = new File(imgPath + imageName);
//						if (f.exists()) {
//							DocumentBuilder builder = new DocumentBuilder(doc);// 新建文档节点
//							builder.moveTo(shape);// 移动到图片位置
//							builder.write("[img]"
//									+ fileName.substring(0, fileName
//											.lastIndexOf("."))
//											+ "/"
//											+ f.getName().substring(0,
//													f.getName().lastIndexOf("."))
//													+ ".png" + "[/img]");// 插入替换文本
//							shape.remove();// 移除图形
//						} else {
//							log.error("图片不存在！");
//							continue;
//						}
//					}
//				}
//				String extName = fileName.substring(fileName.lastIndexOf("."));
//				String mainName = fileName.substring(0, fileName
//						.lastIndexOf("."));
//				doc.save(filePath + mainName + "_done" + extName);// 保存修改后的文档
//				// log.info("filename---->" + mainName + "_done" + extName);
//				return mainName + "_done" + extName;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//}