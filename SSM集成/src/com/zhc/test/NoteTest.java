package com.zhc.test;

import com.zhc.pojo.Note;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;

public class NoteTest {
    public static void main(String[] args) throws Exception{
        //创建一个jaxb对象
        JAXBContext jaxb = JAXBContext.newInstance(Note.class);

        //创建一个xml解析对象
        Unmarshaller un = jaxb.createUnmarshaller();

        //读取资源
        InputStream is = NoteTest.class.getResourceAsStream("/note.xml");

        //解析xml返回一个Note对象
        Note note = (Note) un.unmarshal(is);
        System.out.println("note:" + note);
    }
}
