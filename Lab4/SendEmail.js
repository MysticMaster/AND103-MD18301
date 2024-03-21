// Gửi email
// B1. Bật bảo mật 2 lớp của email
// mped onxn oxne nkbb
// Cài thư viện npm i nodemailer
const express = require('express')
const nodemailer = require('nodemailer')
// tạo server
const app = express()
// tạo bộ gửi mail
let boGuiMail = nodemailer.createTransport({
    service: 'gmail',
    auth:{
        user:'anhdnph31267@fpt.edu.vn',
        pass: 'mped onxn oxne nkbb'
    }
})

// thiết lập nội dung gửi

let noiDungGui = {
    from: 'anhdnph31267@fpt.edu.vn',
    to: 'dark.mystic.slayer@gmail.com',
    subject:'Test email',
    text: 'Lab 4 PH31267 Đinh Ngọc Anh'
}
// Thực hiện gửi
boGuiMail.sendMail(noiDungGui,(err,info)=>{
    if(err){
        console.log('Lỗi gửi mail', err)
    }else{
        console.log('Đã gửi: ', info.response)
    }
})
// Chạy server 
app.listen(3005,()=>{
    console.log('server đang chạy ở cồng 3005')
})