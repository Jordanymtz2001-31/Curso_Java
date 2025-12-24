import { Routes } from '@angular/router';
import { ListasC } from './Componente/Cliente/listas-c/listas-c';
import { ListaM } from './Componente/Mascota/lista-m/lista-m';
import { ListaR } from './Componente/Responsable/lista-r/lista-r';
import { ListaV } from './Componente/Veterinaria/lista-v/lista-v';
import { GuardarC } from './Componente/Cliente/guardar-c/guardar-c';
import { GurdarM } from './Componente/Mascota/gurdar-m/gurdar-m';
import { GuardarR } from './Componente/Responsable/guardar-r/guardar-r';
import { GuardarV } from './Componente/Veterinaria/guardar-v/guardar-v';
import { EditarC } from './Componente/Cliente/editar-c/editar-c';
import { EditarM } from './Componente/Mascota/editar-m/editar-m';
import { EditarR } from './Componente/Responsable/editar-r/editar-r';
import { EditarV } from './Componente/Veterinaria/editar-v/editar-v';
import { DetalleC } from './Componente/Cliente/detalle-c/detalle-c';
import { DetalleR } from './Componente/Responsable/detalle-r/detalle-r';
import { DetalleV } from './Componente/Veterinaria/detalle-v/detalle-v';

export const routes: Routes = [

    //Aqui van las rutas de navegacion de la aplicacion que vienen de app.ts
    //Para unir las vistas con los metodos de navegacion

    //LISTAR--------------------------------------------------------------------------------------
    {path: 'listar/clientes', component: ListasC},
    {path: 'listar/mascotas', component: ListaM},
    {path: 'listar/responsables', component: ListaR},
    {path: 'listar/veterinarias', component: ListaV},

    //GUARDAR--------------------------------------------------------------------------------------
    {path: 'guardar/cliente', component: GuardarC},
    {path: 'guardar/mascota', component: GurdarM},
    {path: 'guardar/responsable', component: GuardarR},
    {path: 'guardar/veterinaria', component: GuardarV},

    //EDITAR------------------------------------------------------------------------------------
    {path: 'editar/cliente', component: EditarC},
    {path: 'editar/mascota', component: EditarM},
    {path: 'editar/responsable', component: EditarR},
    {path: 'editar/veterinaria', component: EditarV},

    //DETALLES-------------------------------------------------------------------------------
    {path: 'detalle/cliente', component: DetalleC},
    {path: 'detalle/responsables', component: DetalleR},
    {path: 'detalle/veterinaria', component: DetalleV}
];
