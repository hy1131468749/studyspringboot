package com.glodio.util;

import java.lang.reflect.Field;

/*
 * @function CopyBean 2��beanȫ���ֶθ�ֵ
 * @package com.raycomm.util
 * @filename CopyBean
 * @create 2017-1-3
 * */
public class CopyBean {
	
	/*
	 * @function target �����ֶ�ֵ��ֵ��source�����ֶ�ֵ
	 * @param target Ŀ��bean
	 * @param source Դbean ���ݿ�����¼
	 */



	//CopyBean.Target2Src(queryBean,bean);
    public  static void Target2Src(Object source,Object target) {
    	Class<?> srcClass = source.getClass();
    	Field[] fieldsSuper = null;
    	Field[] fields =null;
    	
        fields = srcClass.getDeclaredFields();	//�õ�bean�ֶ�����
        
        for(; srcClass != Object.class;srcClass = srcClass.getSuperclass()){//�õ������ֶμ���
        	fieldsSuper = srcClass.getDeclaredFields();
        	
        }
    	//System.out.println(fieldsSuper.length);
    	//System.out.println(fields.length);
    	
        for(int i =0;i < fieldsSuper.length;i++) {
        	fieldsSuper[i].setAccessible(true);  //���������޸��ֶ�
            String nameKey = fieldsSuper[i].getName();

            try {
				if ((fieldsSuper[i].get(target) != null ) && ((fieldsSuper[i].get(source)) != ((fieldsSuper[i].get(target))))) {	//2��bean�ֶβ�ͬ����ֵ��source�ֶ�
					if(!nameKey.equals("id")) {
						try {
							fieldsSuper[i].set(source, fieldsSuper[i].get(target));	//��ֵsource�ֶ�
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}
					}
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
        }
            
        for(int i =0;i < fields.length;i++) {
        	fields[i].setAccessible(true);  //���������޸��ֶ�
            String nameKey = fields[i].getName();
            
            try {
				if (((fields[i].get(source)) != ((fields[i].get(target))))) {	//2��bean�ֶβ�ͬ����ֵ��source�ֶ�
					if(!nameKey.equals("id")) {
						try {
				    		fields[i].set(source, fields[i].get(target));	//��ֵsource�ֶ�
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}
					}
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
        }
    }

}
