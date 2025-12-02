import React from 'react';
import { clsx } from 'clsx';

/**
 * 入力フィールドコンポーネントのプロパティ
 */
export interface InputProps extends Omit<React.InputHTMLAttributes<HTMLInputElement>, 'size'> {
  /** ラベルテキスト */
  label?: string;
  /** エラーメッセージ */
  error?: string;
  /** ヘルプテキスト */
  helpText?: string;
  /** 左側のアイコン */
  leftIcon?: React.ReactNode;
  /** 右側のアイコン */
  rightIcon?: React.ReactNode;
  /** 入力フィールドのサイズ */
  size?: 'sm' | 'md' | 'lg';
}

/**
 * 洗練された入力フィールドコンポーネント
 * 
 * ラベル、エラー表示、ヘルプテキスト、アイコンをサポートする
 * アクセシブルな入力フィールドコンポーネントです。
 */
export const Input: React.FC<InputProps> = ({
  label,
  error,
  helpText,
  leftIcon,
  rightIcon,
  size = 'md',
  className,
  id,
  ...props
}) => {
  const inputId = id || `input-${Math.random().toString(36).substr(2, 9)}`;

  const sizeClasses = {
    sm: 'px-3 py-1.5 text-sm',
    md: 'px-4 py-2 text-sm',
    lg: 'px-4 py-3 text-base'
  };

  return (
    <div className="w-full">
      {/* ラベル */}
      {label && (
        <label
          htmlFor={inputId}
          className="block text-sm font-medium text-secondary-700 mb-1"
        >
          {label}
        </label>
      )}
      
      {/* 入力フィールドコンテナ */}
      <div className="relative">
        {/* 左側のアイコン */}
        {leftIcon && (
          <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
            <div className="h-5 w-5 text-secondary-400">
              {leftIcon}
            </div>
          </div>
        )}
        
        {/* 入力フィールド */}
        <input
          id={inputId}
          className={clsx(
            'w-full border rounded-lg transition-colors duration-200',
            'focus:outline-none focus:ring-2 focus:ring-primary-500 focus:border-transparent',
            'disabled:bg-secondary-50 disabled:text-secondary-500 disabled:cursor-not-allowed',
            sizeClasses[size],
            leftIcon && 'pl-10',
            rightIcon && 'pr-10',
            error
              ? 'border-red-300 focus:ring-red-500'
              : 'border-secondary-300 focus:ring-primary-500',
            className
          )}
          {...props}
        />
        
        {/* 右側のアイコン */}
        {rightIcon && (
          <div className="absolute inset-y-0 right-0 pr-3 flex items-center pointer-events-none">
            <div className="h-5 w-5 text-secondary-400">
              {rightIcon}
            </div>
          </div>
        )}
      </div>
      
      {/* エラーメッセージ */}
      {error && (
        <p className="mt-1 text-sm text-red-600">
          {error}
        </p>
      )}
      
      {/* ヘルプテキスト */}
      {helpText && !error && (
        <p className="mt-1 text-sm text-secondary-500">
          {helpText}
        </p>
      )}
    </div>
  );
};
