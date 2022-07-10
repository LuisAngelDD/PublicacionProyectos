const express = require('express');
const expRt = express.Router();
const ctrlProyect = require('../controles/proyectos');
const {checkToken} = require('../libs/tokens')
expRt.get('/proyecto/get/:status/:type/', ctrlProyect.getAllProyects)
expRt.get('/proyecto/get/:status/:type/:nombre', ctrlProyect.getAllProyectNombre)
expRt.get('/proyecto/code/:code', ctrlProyect.getProyect)
expRt.get('/proyecto/myProyects/:status/:type/', checkToken,ctrlProyect.myProyects)
expRt.get('/proyecto/myProyects/:status/:type/:nombre', checkToken,ctrlProyect.myProyectsNombre)
expRt.post('/proyecto/post/',checkToken,ctrlProyect.postProyect)
expRt.put('/proyecto/update/:proyectoId',ctrlProyect.updateProyect)
module.exports = expRt;