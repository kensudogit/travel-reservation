import React from 'react';
import { clsx } from 'clsx';

/**
 * カードコンポーネントのプロパティ
 */
export interface CardProps {
  /** カードのクラス名 */
  className?: string;
  /** 子要素 */
  children: React.ReactNode;
  /** ホバー効果を有効にするか */
  hover?: boolean;
  /** カードの影の強さ */
  shadow?: 'sm' | 'md' | 'lg';
}

/**
 * カードヘッダーコンポーネントのプロパティ
 */
export interface CardHeaderProps {
  /** ヘッダーのクラス名 */
  className?: string;
  /** 子要素 */
  children: React.ReactNode;
}

/**
 * カードボディコンポーネントのプロパティ
 */
export interface CardBodyProps {
  /** ボディのクラス名 */
  className?: string;
  /** 子要素 */
  children: React.ReactNode;
}

/**
 * カードフッターコンポーネントのプロパティ
 */
export interface CardFooterProps {
  /** フッターのクラス名 */
  className?: string;
  /** 子要素 */
  children: React.ReactNode;
}

/**
 * 洗練されたカードコンポーネント
 * 
 * コンテンツを整理して表示するためのカードコンポーネントです。
 * ヘッダー、ボディ、フッターの構造化されたレイアウトを提供します。
 */
export const Card: React.FC<CardProps> = ({
  className,
  children,
  hover = false,
  shadow = 'md'
}) => {
  const shadowClasses = {
    sm: 'shadow-sm',
    md: 'shadow-md',
    lg: 'shadow-lg'
  };

  return (
    <div
      className={clsx(
        'bg-white rounded-xl border border-secondary-200 overflow-hidden',
        shadowClasses[shadow],
        hover && 'hover:shadow-xl transition-shadow duration-300',
        className
      )}
    >
      {children}
    </div>
  );
};

/**
 * カードヘッダーコンポーネント
 */
export const CardHeader: React.FC<CardHeaderProps> = ({ className, children }) => {
  return (
    <div className={clsx('px-6 py-4 border-b border-secondary-200', className)}>
      {children}
    </div>
  );
};

/**
 * カードボディコンポーネント
 */
export const CardBody: React.FC<CardBodyProps> = ({ className, children }) => {
  return (
    <div className={clsx('px-6 py-4', className)}>
      {children}
    </div>
  );
};

/**
 * カードフッターコンポーネント
 */
export const CardFooter: React.FC<CardFooterProps> = ({ className, children }) => {
  return (
    <div className={clsx('px-6 py-4 border-t border-secondary-200 bg-secondary-50', className)}>
      {children}
    </div>
  );
};
