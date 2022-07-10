const roles = require('../modelos/roles');
const tpo = require('../modelos/tpo');
const stt = require('../modelos/statusPr');
const crearInicial = async () => {
    try {
        const count = await roles.estimatedDocumentCount();
        if (count >0 ){

        }else {
            const values = await Promise.all([
                new roles({nombreRol:"autentificado"}).save(),
                new roles({nombreRol:"none"}).save()
            ]);
            console.log(values)
        }     
    } catch (error) {
        console.log(error)
    }
    try {
        const count = await tpo.estimatedDocumentCount();
        if (count >0 ) {

        }else {
            const values = await Promise.all([
                new tpo({nombreTipoProyecto:"Proyecto Social"}).save(),
                new tpo({nombreTipoProyecto:"Proyecto Educativo"}).save(),
                new tpo({nombreTipoProyecto:"Proyecto Comunitarios"}).save(),
                new tpo({nombreTipoProyecto:"Proyecto Academicos"}).save(),
                new tpo({nombreTipoProyecto:"Proyecto Investigacion"}).save(),
                new tpo({nombreTipoProyecto:"Proyecto Produccion"}).save()
            ]);
            console.log(values)
        }
    } catch (error) {
        console.log(error)
    }
    try {
        const count = await stt.estimatedDocumentCount();
        if (count >0 ) {

        }else {
            const values = await Promise.all([
                new stt({statusProyect:"Proyecto Activo"}).save(),
                new stt({statusProyect:"Proyecto Pausado"}).save(),
                new stt({statusProyect:"Proyecto Cancelado"}).save(),
            ]);
            console.log(values)
        }
    } catch (error) {
        console.log(error)
    } 
} 
module.exports = crearInicial;