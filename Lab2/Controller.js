const express = require('express');
const mongoose=require('mongoose');
const sinhvien=require('./SinhVienModel');

const controller=express();
controller.set('view engine','ejs');

mongoose.connect('mongodb://localhost:27017/DBLab1',{
    useNewUrlParser: true,
    useUnifiedTopology: true
}).then(()=>{
    console.log('ket noi thanh cong voi db');
}).catch((err)=>{
    console.error("Loi: "+err);
});

controller.get('/sinhvien',async (req,res)=>{
    try {
        const sinhviens = await sinhvien.find();
        res.render('sinhvien',{sinhviens: sinhviens});
        console.log(sinhviens);
    } catch (error) {
        console.error(error);
        res.status(500).json({error: 'Server chay loi'});
    }
});
const PORT = process.env.PORT||5000;
controller.listen(PORT,()=>{
    console.log("Server dang chay o cong 5000");
})