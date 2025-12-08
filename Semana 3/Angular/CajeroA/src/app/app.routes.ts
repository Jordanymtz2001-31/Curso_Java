import { Routes } from '@angular/router';
import { CajeroA } from './Componente/cajero-a/cajero-a';

export const routes: Routes = [
    //Aquí van las rutas de la aplicación que viene del archivo app.ts
    // Para unir las rutas con los componentes
    {
        path: '',
        component: CajeroA
    }
];
