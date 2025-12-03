import { Routes } from '@angular/router';
import { Guardar } from './Componente/guardar/guardar';
import { Editar } from './Componente/editar/editar';
import { Listar } from './Componente/listar/listar';

export const routes: Routes = [

    //Aquí van las rutas de la aplicación que viene del archivo app.ts
    // Para unir las rutas con los componentes

    {
        path: 'lista',
        component: Listar
    },
    {
        path: 'guardar',
        component: Guardar
    },
    {
        path: 'editar',
        component: Editar
    }

];
