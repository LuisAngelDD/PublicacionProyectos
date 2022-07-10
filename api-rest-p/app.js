const express = require('express');
//const bodyParser = require('body-parser');
const cors = require('cors');
const app = express();
const rtPr = require('./rutas/proyectos');
const rtUs = require('./rutas/usuarios');
const rtLk = require('./rutas/likes');
const rtSt = require('./rutas/status')
const rtTy = require('./rutas/type')
const rtFl = require('./rutas/follows')
app.use(cors());
app.use(express.urlencoded({extended:false}));
app.use(express.json());
app.get('/app', (req,res)=>{
    res.send({message:'Bienvenido'});
});
//routes
app.use('/app',rtPr)
app.use('/app',rtUs)
app.use('/app',rtLk)
app.use('/app',rtSt)
app.use('/app',rtTy)
app.use('/app',rtFl)

module.exports = app;