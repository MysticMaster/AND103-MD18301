const express = require('express')
const mongoose = require('mongoose')

const ShoesRoute = require('./routes/ShoesRoute')


const app = express() 

mongoose.connect('mongodb://localhost:27017/ASMServer', {
    useNewUrlParser: true,
    useUnifiedTopology: true
}).then(() => {
    console.log('Kết nối thành công với mongodb')
}).catch((err) => {
    console.error('Lỗi: ' + err)
})

app.use(express.json())

app.use('/', ShoesRoute)

app.set('view engine', 'ejs')
 
const PORT = process.env.PORT || 5000
app.listen(PORT, () => {
    console.log('Server đang chạy')
})