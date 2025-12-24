import { ApplicationConfig, importProvidersFrom, provideZoneChangeDetection, provideBrowserGlobalErrorListeners } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { provideHttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(), // Manejo global de errores
    provideRouter(routes), // Configuración de rutas
    provideZoneChangeDetection({eventCoalescing: true}), // Detección de cambios optimizada
    importProvidersFrom(FormsModule), // Importación de FormsModule para formularios
    provideHttpClient() // Proveedor de HttpClient para solicitudes HTTP
  ]
};
