const mongoose=require('mongoose');
const ModelSinhVien = new mongoose.Schema({
    id:{
        type: String,
        require: true
    },
    name:{
        type: String,
        require: true
    },
});
const sinhvien=mongoose.model('sinhviens',ModelSinhVien);
module.exports = sinhvien;